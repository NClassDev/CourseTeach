import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.loginclean.R
import com.example.loginclean.core.BaseViewHolder
import com.example.loginclean.data.source.Cursos
import com.example.loginclean.ui.HomeFragmentDirections
import kotlinx.android.synthetic.main.cursos_item.view.*

class CursosAdapter(private val context: Context, private val cursosList: List<Cursos> ,
private val cursoClickListener : OnCursoClickListener
                    ) :
    RecyclerView.Adapter<BaseViewHolder<*>> ( ){

    interface OnCursoClickListener{
        fun onCursoClick(curso: Cursos, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return MainViewHolderByIntolerance(LayoutInflater.from(context).inflate(R.layout.cursos_item, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is CursosAdapter.MainViewHolderByIntolerance -> holder.bind(cursosList[position], position)
        }
    }

    override fun getItemCount(): Int {
        return cursosList.size
    }

    inner class MainViewHolderByIntolerance(itemView: View): BaseViewHolder<Cursos>(itemView){
        override fun bind(curso :Cursos, position: Int) {

            itemView.apply {
                itemView.cursos_item_id_text.text = "id: "+ curso.idcurso
                itemView.curso_item_name_tv.text = curso.namecurso
                itemView.curso_item_horario_tv.text = curso.horario

            }


            itemView.setOnClickListener {
               cursoClickListener.onCursoClick(curso, position)
//                val action = HomeFragmentDirections.actionHomeFragmentToCursoDetailFragment(curso)
//                itemView.findNavController().navigate(action)
            }

        }
    }

}