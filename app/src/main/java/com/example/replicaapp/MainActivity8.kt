package com.example.replicaapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.example.replicaapp.databinding.ActivityMain8Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity8 : AppCompatActivity() {

    private lateinit var binding: ActivityMain8Binding
    private lateinit var database: DatabaseReference
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var currentUser: FirebaseUser
    private lateinit var nombreUsuario: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain8Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar la referencia a la base de datos
        database = FirebaseDatabase.getInstance().reference.child("chat")

        // Obtener el usuario actualmente autenticado
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        currentUser = auth.currentUser!!

        // Obtener el nombre de usuario del perfil
        nombreUsuario = currentUser.displayName ?: ""

        // Configurar el RecyclerView y el adaptador de mensajes
        messageAdapter = MessageAdapter(ArrayList(), nombreUsuario)
        binding.chatRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.chatRecyclerView.adapter = messageAdapter


        // Escuchar los cambios en los mensajes
        database.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val message = snapshot.getValue(Message::class.java)
                message?.let {
                    messageAdapter.addMessage(it)
                    binding.chatRecyclerView.smoothScrollToPosition(messageAdapter.itemCount - 1)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                // Se llama cuando un mensaje existente ha sido modificado en la base de datos
                val updatedMessage = snapshot.getValue(Message::class.java)
                updatedMessage?.let {
                    val messageId = snapshot.key
                    val index = messageAdapter.indexOfMessage(messageId)
                    if (index != -1) {
                        // Actualizar el mensaje en la lista y notificar al adaptador del cambio
                        messageAdapter.updateMessage(index, it)
                    }
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                // Se llama cuando un mensaje existente ha sido eliminado de la base de datos
                val messageId = snapshot.key
                val index = messageAdapter.indexOfMessage(messageId)
                if (index != -1) {
                    // Eliminar el mensaje de la lista y notificar al adaptador del cambio
                    messageAdapter.removeMessage(index)
                }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                // No es necesario implementar esto en el ejemplo, pero puedes manejar el movimiento de mensajes si lo necesitas.
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejar errores de lectura de la base de datos si es necesario.
            }

        })

        // Escuchar el evento del botón de enviar mensaje
        binding.sendButton.setOnClickListener {
            sendMessage()
        }


        binding.imageView7.setOnClickListener {
            val intent = android.content.Intent(this, MainActivity7::class.java)
            startActivity(intent)
        }
    }

    private fun sendMessage() {
        val sender = if (nombreUsuario.isNotEmpty()) nombreUsuario else "Usuario"
        val messageText = binding.messageEditText.text.toString().trim()

        if (messageText.isNotEmpty()) {
            val messageId = database.push().key
            val timestamp = System.currentTimeMillis()

            val message = Message(messageId, sender, messageText, timestamp)
            messageId?.let {
                database.child(it).setValue(message)
            }

            binding.messageEditText.text.clear()
        }
    }
}
