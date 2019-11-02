package self.yang.application

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

const val EXTRA_MESSAGE = "self.yang.application.MESSAGE"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * 设置蓝牙可以被其他设备搜索到
     */
    private fun setBluetoothCanDiscovered() {
        var bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        if (bluetoothAdapter.scanMode !== BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            val discoverableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0)
            startActivity(discoverableIntent)

            callToast("设置蓝牙已可被其他设备搜索到")
        }
    }

    /**
     * 用户授权选择结果回调
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (Activity.RESULT_CANCELED == resultCode) {
            callToast("用户拒绝授权")
        } else if (Activity.RESULT_OK == resultCode) {
            callToast("用户授权成功")
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * 调用系统页面打开蓝牙
     */
    fun openBluetooth(view: View) {
        var notSupportBluetooth = !isSupportBluetooth()

        if (notSupportBluetooth) {
            return
        }

        var bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        var bluetoothIsOpened = bluetoothAdapter.isEnabled

        if (bluetoothIsOpened) {
            callToast("蓝牙已打开")

            setBluetoothCanDiscovered()

            return
        }

        startActivityForResult(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 1)

        setBluetoothCanDiscovered()
    }

    /**
     * 关闭蓝牙
     */
    fun closeBluetooth(view: View) {
        var notSupportBluetooth = !isSupportBluetooth()

        if (notSupportBluetooth) {
            return
        }

        var bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        var bluetoothIsOpened = bluetoothAdapter.isEnabled

        if (bluetoothIsOpened) {
            bluetoothAdapter.disable()

            return
        }

        callToast("蓝牙已关闭")

        return
    }

    /**
     * 判断设备是否支持蓝牙
     */
    private fun isSupportBluetooth(): Boolean {
        var bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        if (bluetoothAdapter == null || !packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH)) {
            callToast("当前设备不支持蓝牙")

            return false
        }

        return true
    }

    /**
     * 调用Toast打印提示信息
     */
    private fun callToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    /**
     * Called when the user taps the Send button
     */
    fun sendMessage(view: View) {
        // Do something in response to button
        val editText = findViewById<EditText>(R.id.editText)
        val message = editText.text.toString()

        val intent = Intent(this, DisplayMessageActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
        }

        startActivity(intent)
    }

}
