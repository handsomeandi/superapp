package com.example.superapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.coroutines.superapp.R

class ComponentsAdapter(private val navController: NavController) :
    RecyclerView.Adapter<ComponentHolder>() {
    private val components = listOf(
        Component(
            "Service",
            R.id.serviceFragment
        )
    )

    override fun getItemCount() = components.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComponentHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.component_layout, parent, false)
        return ComponentHolder(view, navController)
    }

    override fun onBindViewHolder(holder: ComponentHolder, position: Int) {
        holder.bind(components[position])
    }


}

class ComponentHolder(private val view: View, private val navController: NavController) :
    RecyclerView.ViewHolder(view) {
    fun bind(component: Component) {
        view.findViewById<LinearLayout>(R.id.componentContainer).apply {
            setOnClickListener {
                navController.navigate(component.navigationId)
            }
        }
        view.findViewById<TextView>(R.id.componentName).apply {
            text = component.name
        }
    }
}
