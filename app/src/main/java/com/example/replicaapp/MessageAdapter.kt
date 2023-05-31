    package com.example.replicaapp

    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.TextView
    import androidx.recyclerview.widget.RecyclerView


    class MessageAdapter(private val messages: MutableList<Message>, private val nombreUsuario: String) :
        RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

        inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val senderTextView: TextView = itemView.findViewById(R.id.senderTextView)
            val messageTextView: TextView = itemView.findViewById(R.id.messageTextView)
            val timestampTextView: TextView = itemView.findViewById(R.id.timestampTextView)
            // Obtén el nombre de usuario del usuario actualmente autenticado desde tu base de datos o cualquier otra fuente de datos
            val nombreUsuario = "Nombre de Usuario" // Reemplaza con el nombre de usuario real

            // Obtener el usuario actual desde algún lugar (por ejemplo, una variable en tu actividad o fragmento)
            val currentUser = "nombreUsuario"

            // Crear una instancia del adaptador de mensajes y pasar el usuario actual como argumento
            val messageAdapter = MessageAdapter(messages, currentUser)


        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_chat_message, parent, false)
            return MessageViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
            val message = messages[position]
            // Obtener el usuario actual desde algún lugar (por ejemplo, una variable en tu actividad o fragmento)
            val currentUser = "nombreUsuario"
            // Crear una instancia del adaptador de mensajes y pasar el usuario actual como argumento
            val messageAdapter = MessageAdapter(messages, currentUser)
            holder.senderTextView.text = if (message.sender == currentUser) nombreUsuario else "Usuario"
            holder.messageTextView.text = message.message
            holder.timestampTextView.text = message.timestamp.toString()
        }

        override fun getItemCount(): Int {
            return messages.size
        }


        fun addMessage(message: Message) {
            messages.add(message)
            notifyItemInserted(messages.size - 1)
        }
        fun removeMessage(index: Int) {
            messages.removeAt(index)
            notifyItemRemoved(index)
        }
        fun updateMessage(index: Int, message: Message) {
            messages[index] = message
            notifyItemChanged(index)
        }
        fun indexOfMessage(messageId: String?): Int {
            for ((index, message) in messages.withIndex()) {
                if (message.id == messageId) {
                    return index
                }
            }
            return -1
        }
    }