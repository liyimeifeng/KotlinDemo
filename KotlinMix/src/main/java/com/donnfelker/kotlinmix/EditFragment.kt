package com.donnfelker.kotlinmix

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.donnfelker.kotlinmix.models.Todo
import io.realm.Realm
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.find
import java.util.*


class EditFragment : Fragment() {

    val TODO_ID_KEY: String = "todo_id_key"

    val realm: Realm = Realm.getDefaultInstance()

    var todo: Todo? = null

    companion object {
        fun newInstance(id: String): EditFragment {
            var args: Bundle = Bundle()
            args.putString("todo_id_key", id)
            var editFragment: EditFragment = newInstance()
            editFragment.arguments = args
            return editFragment
        }

        fun newInstance(): EditFragment {
            return EditFragment()
        }
    }


    /**
     * 自写布局
     */
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return UI {
            verticalLayout {
                padding = dip(30)
                var title = editText {
                    id = R.id.todo_title
                    hintResource = R.string.title_hint
                }

                var desc = editText {
                    id = R.id.todo_desc
                    hintResource = R.string.description_hint
                }
                button {
                    id = R.id.todo_add
                    textResource = R.string.add_todo
                    onClick { view -> createTodoFrom(title, desc) } //添加了anko库之后才能直接使用onclick方法
//                    onClick {view -> testWhile()  }
//                    onClick { testFor() }
                }
            }
        }.view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        if(arguments != null && arguments.containsKey(TODO_ID_KEY)) {
            val todoId = arguments.getString(TODO_ID_KEY)
            todo = realm.where(Todo::class.java).equalTo("id", todoId).findFirst()
            val todoTitle = find<EditText>(R.id.todo_title)
            todoTitle.setText(todo?.title)
            val todoDesc = find<EditText>(R.id.todo_desc)
            todoDesc.setText(todo?.description)
            val add = find<Button>(R.id.todo_add)
            add.setText(R.string.save)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }



    /**
     *  A private function to create a TODO item in the database (Realm).
     *
     *  @param title the title edit text.
     *  @param desc the description edit text.
     */
    private fun createTodoFrom(title: EditText, desc: EditText) {
        realm.beginTransaction()

        // Either update the edited object or create a new one.
        var t = todo?: realm.createObject(Todo::class.java)
        t.id = todo?.id?: UUID.randomUUID().toString()
        t.title = title.text.toString()
        t.description = desc.text.toString()
        realm.commitTransaction()

        // Go back to previous activity
        activity.supportFragmentManager.popBackStack();
    }

    ////for循环的测试代码
    private fun testFor() {
        var arr = arrayOf(1, 3, 4, 5, 6)
        for(i in arr.indices) { //通过索引循环，等同于for(int i = 0;i < arr.size();i ++)
            println("=============" + arr[i])
        }
        for(num in arr) { //直接使用数组中的对象循环
            println("=============>>>" +num)
        }
    }

    //while循环的测试代码
    private fun testWhile() {
        println("---------- ")

        var i = 0
        while(i < 10) {
            print(" " + i)
            i++
        }
    }


}
