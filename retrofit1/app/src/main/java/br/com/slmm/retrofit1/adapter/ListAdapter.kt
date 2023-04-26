package br.com.slmm.retrofit1.adapter

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import br.com.slmm.retrofit1.R
import br.com.slmm.retrofit1.model.Question
import kotlinx.android.synthetic.main.question_item.view.*

class ListAdapter(private val context: Context, private val mQuestions: List<Question>, private val mRowLayout: Int) : RecyclerView.Adapter<ListAdapter.QuestionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(mRowLayout, parent, false)
        return QuestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.positionNumber?.text = context.resources.getString(R.string.question_num, position + 1)
        holder.title?.text = context.resources.getString(R.string.ques_title, mQuestions[position].title)
        holder.link?.text = context.resources.getString(R.string.ques_link, mQuestions[position].link)

        holder.containerView.setOnClickListener {
            Toast.makeText(context, "Clicked on: " + holder.title.text, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return mQuestions.size
    }

    class QuestionViewHolder(val containerView: View) : RecyclerView.ViewHolder(containerView) {
        val positionNumber = containerView.positionNumber
        val title = containerView.title
        val link = containerView.link
    }
}
