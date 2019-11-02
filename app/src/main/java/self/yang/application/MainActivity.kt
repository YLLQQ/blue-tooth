package self.yang.application

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


const val EXTRA_MESSAGE = "self.yang.application.MESSAGE"
const val REQUEST_ACCESS_COARSE_LOCATION_PERMISSION = 10

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
     * 调用Toast打印提示信息
     */
    private fun callToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    /**
     * 判断设备是否支持蓝牙
     */
    private fun isSupportBluetooth(): Boolean {
        var bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        if (bluetoothAdapter == null || !packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH)) {
            showBluetoothDialog(getString(R.string.message_bluetooth_is_not_supported))

            return false
        }

        return true
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
            showBluetoothDialog(getString(R.string.message_bluetooth_is_opened))

            return
        }

        startActivityForResult(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 1)
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

        showBluetoothDialog(getString(R.string.message_bluetooth_is_closed))

        return
    }

    /**
     * 展示弹窗
     */
    private fun showBluetoothDialog(message: String) {
        var alertDialog = AlertDialog.Builder(this)

        alertDialog.setTitle(getString(R.string.title_bluetooth))
        alertDialog.setMessage(message)

        alertDialog.create().show()
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
