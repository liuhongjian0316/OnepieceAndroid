package work.aijiu.onepiece.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import work.aijiu.onepiece.ui.main.nav.BottomTabs


class HomeViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private const val TAG = "HomeViewModel"
    }

    private val _position = MutableLiveData(BottomTabs.TAB_FIRST_PAGE)
    val position: LiveData<BottomTabs> = _position


    fun onPositionChanged(position: BottomTabs) {
        _position.value = position
    }


}