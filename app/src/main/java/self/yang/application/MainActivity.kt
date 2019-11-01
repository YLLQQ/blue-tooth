package self.yang.application

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

const val EXTRA_MESSAGE = "self.yang.application.MESSAGE"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * 调用系统页面打开蓝牙
     */
    fun openBluetooth(view: View) {
        var bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        var bluetoothIsOpen = bluetoothAdapter.isEnabled

        if (bluetoothIsOpen) {
            var alertDialog = AlertDialog.Builder(this)

            alertDialog.setTitle(getString(R.string.title_bluetooth))
            alertDialog.setMessage(getString(R.string.message_bluetooth_is_opened))

            alertDialog.create().show()

            return
        }

        startActivityForResult(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 1)
    }

    /**
     * 关闭蓝牙
     */
    fun closeBluetooth(view: View) {
        var bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        var bluetoothIsOpen = bluetoothAdapter.isEnabled

        if (bluetoothIsOpen) {
            bluetoothAdapter.disable()

            return
        }

        var alertDialog = AlertDialog.Builder(this)

        alertDialog.setTitle(getString(R.string.title_bluetooth))
        alertDialog.setMessage(getString(R.string.message_bluetooth_is_closed))

        alertDialog.create().show()

        return
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
