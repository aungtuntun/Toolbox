package com.imceits.aungtuntun.toolsbox.utils

import android.content.Intent
import android.util.SparseArray
import androidx.core.util.forEach
import androidx.core.util.set
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.imceits.aungtuntun.toolsbox.R

fun BottomNavigationView.setupWithNavController(navGraphIds: List<Int>, fragmentManager: FragmentManager,
                           containerId: Int, intent: Intent): LiveData<NavController> {

    val graphIdToTagMap = SparseArray<String>()
    val selectedNavController = MutableLiveData<NavController>()
    var firstFragmentGraphId = 0
    // first, create a NavHostFragment for each NavGraph ID
    navGraphIds.forEachIndexed { index, navGraphId ->
        val fragmentTag = getFragmentTag(index)

        val navHostFragment = obtainNavHostFragment(fragmentManager, fragmentTag, navGraphId, containerId)
        val graphId = navHostFragment.navController.graph.id
        if (index == 0) {
            firstFragmentGraphId = graphId
        }
        graphIdToTagMap[graphId] = fragmentTag
        if (this.selectedItemId == graphId) {
            selectedNavController.value = navHostFragment.navController
            attachNavHostFragment(fragmentManager, navHostFragment, index == 0)
        }else {
            detachNavHostFragment(fragmentManager, navHostFragment)
        }
    }

    var selectedItemTag = graphIdToTagMap[this.selectedItemId]
    val firstFragmentTag = graphIdToTagMap[firstFragmentGraphId]
    var isOnFirstFragment = selectedItemTag == firstFragmentTag
    setOnNavigationItemSelectedListener { item ->
        if (fragmentManager.isStateSaved) {
            false
        }else {
            val newlySelectedItemTag = graphIdToTagMap[item.itemId]
            if (selectedItemTag != newlySelectedItemTag) {
                fragmentManager.popBackStack(firstFragmentTag, POP_BACK_STACK_INCLUSIVE)
                val selectedFragment = fragmentManager.findFragmentByTag(newlySelectedItemTag) as NavHostFragment
                if (firstFragmentTag != newlySelectedItemTag) {
                    fragmentManager.beginTransaction()
                        .setCustomAnimations(
                            R.anim.nav_default_enter_anim,
                            R.anim.nav_default_exit_anim,
                            R.anim.nav_default_pop_enter_anim,
                            R.anim.nav_default_pop_exit_anim)
                        .attach(selectedFragment)
                        .setPrimaryNavigationFragment(selectedFragment)
                        .apply {
                            graphIdToTagMap.forEach { key, value ->
                                if (value != newlySelectedItemTag) {
                                    detach(fragmentManager.findFragmentByTag(firstFragmentTag)!!)
                                }
                            }
                        }
                        .addToBackStack(firstFragmentTag)
                        .setReorderingAllowed(true)
                        .commit()
                }
                selectedItemTag = newlySelectedItemTag
                isOnFirstFragment = selectedItemTag == firstFragmentTag
                selectedNavController.value = selectedFragment.navController
                true
            }else{
                false
            }
        }
    }

    fragmentManager.addOnBackStackChangedListener {
        if (!isOnFirstFragment && !fragmentManager.isOnBackStack(firstFragmentTag)) {
            this.selectedItemId = firstFragmentGraphId
        }
        selectedNavController.value?.let { controller ->
            if (controller.currentDestination == null) {
                controller.navigate(controller.graph.id)
            }
        }
    }
    return selectedNavController
}

private fun BottomNavigationView.setItemReselected(graphIdToTagMap: SparseArray<String>, fragmentManager: FragmentManager) {
    setOnNavigationItemReselectedListener { item ->
        val newlySelectedItemTag = graphIdToTagMap[item.itemId]
        val selectedFragment = fragmentManager.findFragmentByTag(newlySelectedItemTag) as NavHostFragment
        val navController = selectedFragment.navController
        navController.popBackStack(navController.graph.startDestination, true)
    }
}

private fun attachNavHostFragment(fragmentManager: FragmentManager, navHostFragment: NavHostFragment, isPrimaryNavFragment: Boolean) {
    fragmentManager.beginTransaction()
        .attach(navHostFragment)
        .apply {
            if (isPrimaryNavFragment) {
                setPrimaryNavigationFragment(navHostFragment)
            }
        }.commitNow()
}

private fun detachNavHostFragment(fragmentManager: FragmentManager, navHostFragment: NavHostFragment) {
    fragmentManager.beginTransaction()
        .detach(navHostFragment)
        .commitNow()
}

private fun obtainNavHostFragment(fragmentManager: FragmentManager, fragmentTag: String, navGraphId: Int, containerId: Int): NavHostFragment {
    val existingFragment = fragmentManager.findFragmentByTag(fragmentTag) as NavHostFragment?
    existingFragment?.let { return it }
    val navHostFragment = NavHostFragment.create(navGraphId)
    fragmentManager.beginTransaction()
        .add(containerId, navHostFragment, fragmentTag)
        .commitNow()
    return navHostFragment
}

private fun FragmentManager.isOnBackStack(backStackName: String) : Boolean {
    val backStackCount = backStackEntryCount
    for (index in 0 until backStackCount) {
        if (getBackStackEntryAt(index).name == backStackName) {
            return true
        }
    }
    return false
}

private fun getFragmentTag(index: Int) = "bottomNavigation#$index"