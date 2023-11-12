package net.azarquiel.note.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.azarquiel.note.R
import net.azarquiel.note.model.Note


class CustomAdapter(val context: Context,
                    val layout: Int
                    ) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private var dataList: List<Note> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewlayout = layoutInflater.inflate(layout, parent, false)
        return ViewHolder(viewlayout, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    internal fun setNotas(notas: List<Note>) {
        this.dataList = notas
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Note){
            // itemview es el item de diseño
            // al que hay que poner los datos del objeto dataItem
            val tvtitulorownota = itemView.findViewById(R.id.tvtitulorownota) as TextView
            val tvdescripcionrownota = itemView.findViewById(R.id.tvdescripcionrownota) as TextView
            val tvfecharownota = itemView.findViewById(R.id.tvfecharownota) as TextView

            tvtitulorownota.text = dataItem.titulo
            tvdescripcionrownota.text = dataItem.descripcion
            if (dataItem.descripcion.length>20)
                tvdescripcionrownota.text = dataItem.descripcion.substring(0, 20)
            tvfecharownota.text = dataItem.fecha

            itemView.tag = dataItem

        }

    }
}