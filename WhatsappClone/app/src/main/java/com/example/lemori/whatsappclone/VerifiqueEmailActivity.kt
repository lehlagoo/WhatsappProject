package com.example.lemori.whatsappclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_verifique_email.*

class VerifiqueEmailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_verifique_email)

        val auth = FirebaseAuth.getInstance()

        tvEmail.text = auth.currentUser!!.email

        btnVerifiquei.setOnClickListener {

            carregando(true)

            btnVerifiquei.text = "Carregando"

            auth.currentUser!!.reload().addOnCompleteListener {
                if (auth.currentUser!!.isEmailVerified) {

                    abreMainActivity()

                } else {

                    toast("Parece que seu em-amil ainda não foi verificado.")

                    btnVerifiquei.text = "Verifiquei"
                    carregando(false)

                }
            }

        }

        btnSair.setOnClickListener {
            auth.signOut()
            abreMainActivity()
        }

        btnReenviar.setOnClickListener {

            carregando(true)

            btnReenviar.text = getString(R.string.carregando)

            auth.currentUser!!.sendEmailVerification().addOnCompleteListener { envioEmail ->

                if (envioEmail.isSuccessful) {

                    toast("E-mail de verificação re-enviado com sucesso")


                } else {
                    toast(envioEmail.exception?.message!!)
                }

                carregando(false)
                btnReenviar.text = "Não recebi o e-mail"

            }
        }

    }

    private fun carregando(flag: Boolean) {

        btnReenviar.isEnabled = !flag
        btnSair.isEnabled = !flag
        btnVerifiquei.isEnabled = !flag

    }

    private fun toast(s: String) {

        Toast.makeText(this, s, Toast.LENGTH_LONG).show()

    }

    private fun abreMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}