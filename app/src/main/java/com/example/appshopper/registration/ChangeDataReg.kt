package com.example.appshopper.registration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appshopper.R

class ChangeDataReg : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title="Alterar Cadastro"
        setContentView(R.layout.activity_change_data_reg)
    }
}
