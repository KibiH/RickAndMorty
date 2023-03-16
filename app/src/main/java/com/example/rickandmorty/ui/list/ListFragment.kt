package com.example.rickandmorty.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmorty.MainActivity
import com.example.rickandmorty.data.CharacterData
import com.example.rickandmorty.databinding.FragmentListBinding


class ListFragment : ReturnList, Fragment() {

    private var _binding: FragmentListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val listViewModel =
            ViewModelProvider(this).get(ListViewModel::class.java)

        _binding = FragmentListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val listView: ListView = binding.charactersList
        listViewModel.list.observe(viewLifecycleOwner) {
            val adapter: ArrayAdapter<*> = ListAdapter(requireActivity(), it)
            listView.adapter = adapter
        }

        listView.onItemClickListener = AdapterView.OnItemClickListener {
                parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as CharacterData
            Log.d("RickAndMorty", "Selected + " + selectedItem.name)
            // here we need to open the details window
            val main = activity as MainActivity
            main.moveToDetails(selectedItem)
        }
        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            activity?.finish() // don't go back to splash screen
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val main = activity as MainActivity
        main.clearList()
        main.updateList(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun gotList(characterList: ArrayList<CharacterData>) {
        val adapter: ArrayAdapter<*> = ListAdapter(requireActivity(), characterList)
        val listView: ListView = binding.charactersList
        listView.adapter = adapter

    }
}