package com.example.loginclean.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.loginclean.R
import com.example.loginclean.core.BaseViewHolder
import com.example.loginclean.data.source.AlumnosEntity
import kotlinx.android.synthetic.main.alumnos_list_item.view.*

class AlumnosRoomAdapter(private val context: Context,
                         private val itemClickListener: OnAlumnoClickListener
                     ) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var alumnosList = listOf<AlumnosEntity>()
    
    
    fun setAlumnosList(alumnosList: List<AlumnosEntity>){
        this.alumnosList = alumnosList
        notifyDataSetChanged()
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return MainViewHolder(
            LayoutInflater.from(context).inflate(R.layout.alumnos_list_item, parent, false)
        )
    }

    interface OnAlumnoClickListener{
        fun onAlumnoClick(alumno: AlumnosEntity, position: Int)
    }


    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is AlumnosRoomAdapter.MainViewHolder -> holder.bind(alumnosList[position], position)
        }
    }

    override fun getItemCount(): Int = alumnosList.size

    inner class MainViewHolder(itemView: View) : BaseViewHolder<AlumnosEntity>(itemView) {
        override fun bind(alumno: AlumnosEntity, position: Int) {

            itemView.apply {
                itemView.alumno_item_name_text.text = alumno.nombre
            }

            itemView.setOnClickListener {
                itemClickListener.onAlumnoClick(alumno, position)
//                Toast.makeText(context, "${alumno.name} ", Toast.LENGTH_LONG).show()
            }
        }
    }

}