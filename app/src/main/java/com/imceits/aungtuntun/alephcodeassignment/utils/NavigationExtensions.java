package com.imceits.aungtuntun.alephcodeassignment.utils;

import android.content.Intent;
import android.util.SparseArray;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.imceits.aungtuntun.alephcodeassignment.R;

import java.util.List;

import kotlin.TypeCastException;

// class for each navigation
public class NavigationExtensions {

    public static LiveData<NavController> setupWithNavController(final BottomNavigationView bottomNavigationView, List<Integer> navGraphIds, FragmentManager fragmentManager, int containerId, Intent intent) {
        final SparseArray<String> graphIdToTagMap = new SparseArray<>();
        final MutableLiveData<NavController> selectedNavController = new MutableLiveData<>();
        int firstFragmentGraphId = 0;
        for (int index = 0; index < navGraphIds.size(); index++) {
            int navGraphId = navGraphIds.get(index);
            String fragmentTag = getFragmentTag(index);
            NavHostFragment navHostFragment = obtainNavHostFragment(fragmentManager, fragmentTag, navGraphId, containerId);
            NavController navController = navHostFragment.getNavController();
            NavGraph navGraph = navController.getGraph();
            int graphId = navGraph.getId();
            if (index == 0) {
                firstFragmentGraphId = graphId;
            }
            graphIdToTagMap.put(graphId, fragmentTag);
            int selectedId = bottomNavigationView.getSelectedItemId();
            if (selectedId == graphId) {
                selectedNavController.setValue(navHostFragment.getNavController());
                attachNavHostFragment(fragmentManager, navHostFragment, index ==0);
            } else {
                detachNavHostFragment(fragmentManager, navHostFragment);
            }
        }

        String selectedTag = graphIdToTagMap.get(bottomNavigationView.getSelectedItemId());
        final String[] selectedItemTag = {selectedTag == null? "" : selectedTag};
        final String firstFragmentTag = graphIdToTagMap.get(firstFragmentGraphId);
        final boolean[] isOnFirstFragment = {selectedItemTag[0].equals(firstFragmentTag)};
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (fragmentManager.isStateSaved())
                return false;
            else {
                final String newlySelectedItemTag = graphIdToTagMap.get(item.getItemId());
                if (!selectedItemTag[0].equals(newlySelectedItemTag)) {
                    fragmentManager.popBackStack(firstFragmentTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    Fragment fragment = fragmentManager.findFragmentByTag(newlySelectedItemTag);
                    if (fragment == null){
                        throw new TypeCastException("null cannot be cast to non-null type androidx.navigation.fragment.NavHostFragment");
                    }
                    NavHostFragment selectedFragment = (NavHostFragment) fragment;
                    if (!firstFragmentTag.equals(newlySelectedItemTag)) {
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.setCustomAnimations(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim,
                                R.anim.nav_default_pop_enter_anim, R.anim.nav_default_pop_exit_anim);
                        transaction.attach(selectedFragment).setPrimaryNavigationFragment(selectedFragment);
                        int indexIv = 0;
                        for (int i = graphIdToTagMap.size(); indexIv < i; ++indexIv) {
                            // Detach all other fragments
                            String fragmentTagItem = graphIdToTagMap.valueAt(indexIv);
                            if (!fragmentTagItem.equals(newlySelectedItemTag)) {
                                Fragment firstFragment = fragmentManager.findFragmentByTag(firstFragmentTag);
                                if (firstFragment != null) {
                                    transaction.detach(firstFragment);
                                }
                            }
                        }
                        transaction.addToBackStack(firstFragmentTag).setReorderingAllowed(true).commit();
                    }
                    selectedItemTag[0] = newlySelectedItemTag;
                    isOnFirstFragment[0] = selectedItemTag[0].equals(firstFragmentTag);
                    selectedNavController.setValue(selectedFragment.getNavController());
                    return true;
                }else {
                    return false;
                }

            }
        });

        // Optional: on item reselected, pop back stack to the destination of the graph
        setupItemReselected(bottomNavigationView, graphIdToTagMap, fragmentManager);

        // Handle deep link
        setupDeepLinks(bottomNavigationView, navGraphIds, fragmentManager, containerId, intent);
    // Finally, ensure that we update our BottomNavigationView when the back stack changes
        int finalFirstFragmentGraphId = firstFragmentGraphId;
        fragmentManager.addOnBackStackChangedListener(() -> {
            if (!isOnFirstFragment[0] && !isOnBackStack(fragmentManager, firstFragmentTag)) {
                bottomNavigationView.setSelectedItemId(finalFirstFragmentGraphId);
            }
            NavController navController = selectedNavController.getValue();
            if (navController != null) {
                if (navController.getCurrentDestination() == null) {
                    NavGraph navGraph = navController.getGraph();
                    navController.navigate(navGraph.getId());
                }
            }
        });
        return selectedNavController;
    }

    private static NavHostFragment obtainNavHostFragment(FragmentManager fragmentManager, String fragmentTag, int navGraphId, int containerId) {
        Fragment existingFragment = fragmentManager.findFragmentByTag(fragmentTag);
        if (existingFragment instanceof NavHostFragment)
            return (NavHostFragment) existingFragment;
        NavHostFragment navHostFragment = NavHostFragment.create(navGraphId);
        fragmentManager.beginTransaction()
                .add(containerId, navHostFragment, fragmentTag)
                .commitNow();
        return navHostFragment;
    }

    private static void setupDeepLinks(BottomNavigationView bottomNavigationView, List<Integer> navGraphIds, FragmentManager fragmentManager, int containerId, Intent intent) {
        for (int index = 0; index < navGraphIds.size(); index++) {
            String fragmentTag = getFragmentTag(index);
          //  int navGraphId = navGraphIds.get(index);
            NavHostFragment navHostFragment = obtainNavHostFragment(fragmentManager, fragmentTag, navGraphIds.get(index), containerId);
            // Handle intent
            if (navHostFragment.getNavController().handleDeepLink(intent) &&
                    bottomNavigationView.getSelectedItemId() != navHostFragment.getNavController().getGraph().getId()) {
                bottomNavigationView.setSelectedItemId(navHostFragment.getNavController().getGraph().getId());
            }
        }
    }

    private static void setupItemReselected(BottomNavigationView bottomNavigationView, SparseArray graphIdToTagMap, FragmentManager fragmentManager) {
        bottomNavigationView.setOnNavigationItemReselectedListener(item -> {
            final String newlySelectedItemTag = (String) graphIdToTagMap.get(item.getItemId());
            Fragment fragment = fragmentManager.findFragmentByTag(newlySelectedItemTag);
            if (fragment instanceof NavHostFragment) {
                NavHostFragment selectedFragment = (NavHostFragment) fragment;
                NavController navController = selectedFragment.getNavController();
                NavGraph navGraph = navController.getGraph();
                navController.popBackStack(navGraph.getStartDestination(), false);
            }
        });
    }

    private static void attachNavHostFragment(FragmentManager fragmentManager, NavHostFragment navHostFragment, boolean isPrimaryNavFragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.attach(navHostFragment);
        if (isPrimaryNavFragment) {
            transaction.setPrimaryNavigationFragment(navHostFragment);
        }
        transaction.commitNow();
    }
    private static void detachNavHostFragment(FragmentManager fragmentManager, NavHostFragment navHostFragment) {
        fragmentManager.beginTransaction()
                .detach(navHostFragment)
                .commitNow();
    }

    private static boolean isOnBackStack(FragmentManager fragmentManager, String backStackName) {
        int backStackCount = fragmentManager.getBackStackEntryCount();
         for (int index = 0; index <= backStackCount; index++) {
             String name = fragmentManager.getBackStackEntryAt(index).getName();
             if (name != null && name.equals(backStackName)) {
                 return true;
             }
         }
         return false;
    }

    private static String getFragmentTag(int index) {
        return "bottomNavigation#" + index;
    }
}
