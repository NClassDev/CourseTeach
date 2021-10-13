import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loginclean.R
import com.example.loginclean.core.BaseViewHolder
import com.example.loginclean.data.source.Cursos
import kotlinx.android.synthetic.main.cursos_item.view.*

class CursosAdapter(private val context: Context, private val cursosList: List<Cursos>) :
    RecyclerView.Adapter<BaseViewHolder<*>> ( ){

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
                itemView.cursos_item_name_text.text = curso.horario
            }

            itemView.setOnClickListener {

            }

        }
    }

}