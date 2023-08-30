package work.aijiu.onepiece.componse

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import work.aijiu.onepiece.R

/**
 * @author   庞世宇
 * @describe 自定义居中弹出dialog
 */
class UpdateDialog(
    private val context: Context, private val layoutResID: Int,
    /**
     * 要监听的控件id
     */
    private val listenedItems: IntArray
) :
    Dialog(context, R.style.MyDialog), View.OnClickListener {

    private var listener: OnCenterItemClickListener? = null
    private var versionNameTv: TextView? = null
    private var versionDescTv: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val window = window
        window!!.setGravity(Gravity.CENTER)
        window.setWindowAnimations(R.style.bottom_menu_animation)
        setContentView(layoutResID)
        val windowManager = (context as Activity).windowManager
        val display = windowManager.defaultDisplay
        val lp = getWindow()!!.attributes
        lp.width = display.width * 4 / 5
        lp.height = display.height / 2
        getWindow()!!.attributes = lp
        setCanceledOnTouchOutside(false)
        versionNameTv = findViewById(R.id.versionNameTv)
        versionDescTv = findViewById(R.id.versionDescTv)

        for (id in listenedItems) {
            findViewById<View>(id).setOnClickListener(this)
        }
    }

    interface OnCenterItemClickListener {
        fun OnCenterItemClick(dialog: UpdateDialog?, view: View?)
    }

    fun setOnCenterItemClickListener(listener: OnCenterItemClickListener?) {
        this.listener = listener
    }

    fun setVersionName(versionName: String) {
        versionNameTv!!.text = versionName
    }

    fun setVersionDesc(versionDesc: String) {
        versionDescTv!!.text = versionDesc
    }

    override fun onClick(view: View) {
        dismiss()
        listener!!.OnCenterItemClick(this, view)
    }


}