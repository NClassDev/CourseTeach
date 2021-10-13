package com.example.loginclean.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.loginclean.R
import com.example.loginclean.core.BaseViewHolder
import com.example.loginclean.data.source.Alumnos
import com.example.loginclean.data.source.AlumnosEntity
import kotlinx.android.synthetic.main.alumnos_list_item.view.*

class AlumnosAdapter(private val context:Context, private val alumnosList: List<Alumnos>):
RecyclerView.Adapter<BaseViewHolder<*>> ()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return MainViewHolder(LayoutInflater.from(context).inflate(R.layout.alumnos_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is AlumnosAdapter.MainViewHolder -> holder.bind(alumnosList[position], position)
        }
    }

    override fun getItemCount(): Int {
        return alumnosList.size
    }

    inner class MainViewHolder(itemView: View): BaseViewHolder<Alumnos>(itemView){
        override fun bind(alumno: Alumnos, position: Int) {

            itemView.apply {
                itemView.alumno_item_name_text.text = alumno.name
            }

            itemView.setOnClickListener {
                Toast.makeText(context, "${alumno.name} ", Toast.LENGTH_LONG).show()
            }
        }
    }

}