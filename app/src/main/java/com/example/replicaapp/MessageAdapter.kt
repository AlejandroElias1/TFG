    package com.example.replicaapp

    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.TextView
    import androidx.recyclerview.widget.RecyclerView
    import java.text.SimpleDateFormat
    import java.util.Date
    import java.util.Locale


    class MessageAdapter(private val messages: MutableList<Message>, private val nombreUsuario: String) :
        RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

        inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val senderTextView: TextView = itemView.findViewById(R.id.senderTextView)
            val messageTextView: TextView = itemView.findViewById(R.id.messageTextView)
            val timestampTextView: TextView = itemView.findViewById(R.id.timestampTextView)


        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_chat_message, parent, false)
            return MessageViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
            val message = messages[position]
            holder.senderTextView.text = message.userProfileName ?: "Usuario" // Mostrar el nombre de perfil del mensaje
            holder.messageTextView.text = message.message
            holder.timestampTextView.text = message.timestamp?.let { convertTimestampToDate(it) }
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

        private fun convertTimestampToDate(timestamp: Long?): String {
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            timestamp?.let {
                val date = Date(it)
                return sdf.format(date)
            }
            return ""
        }

    }