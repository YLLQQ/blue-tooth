package self.yang.application

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
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
        startActivityForResult(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 1);
    }

    /**
     * 关闭蓝牙
     */
    fun closeBluetooth(view: View) {
        var bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        if (null != bluetoothAdapter && bluetoothAdapter.isEnabled) {
            bluetoothAdapter.disable()
        }
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
