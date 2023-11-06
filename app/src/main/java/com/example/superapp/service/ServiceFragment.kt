package com.example.superapp.service

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat.startForegroundService
import androidx.fragment.app.Fragment
import com.example.coroutines.superapp.R


class ServiceFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_service, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<Button>(R.id.playMusicId).apply {
            setOnClickListener {
                startForegroundService(
                    requireContext(),
                    Intent(context, MusicPlayingForegroundService::class.java).apply {
                        action = ACTION.STARTFOREGROUND_ACTION.name
                    })
            }
        }
        view.findViewById<Button>(R.id.pauseMusicId).apply {
            setOnClickListener {
                startForegroundService(
                    requireContext(),
                    Intent(context, MusicPlayingForegroundService::class.java).apply {
                        action = ACTION.STOPFOREGROUND_ACTION.name
                    })
            }
        }
    }
}
