package com.karpolan.android.VideoRecorder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.karpolan.android.VideoRecorder.utils.AudioPlayer;
import com.karpolan.android.VideoRecorder.utils.FlashController;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AudioAndFlashFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AudioAndFlashFragment extends Fragment {
    private AudioPlayer audioPlayer;
    private FlashController flashController;
    private Button btnAudio;
    private Boolean isAudioOn; // State for "Audio" button
    private Button btnFlash;
    private Boolean isFlashOn; // State for "Flash" button

    public AudioAndFlashFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AudioAndFlash.
     */
    // TODO: Rename and change types and number of parameters
    public static AudioAndFlashFragment newInstance(String param1, String param2) {
        AudioAndFlashFragment fragment = new AudioAndFlashFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        audioPlayer = new AudioPlayer();
        flashController = new FlashController(getActivity().getApplicationContext());
        // Show warning message if there is no  Flash Light
        if (!flashController.flashExists) {
            AlertDialog alert = new AlertDialog.Builder(getActivity()).create();
            alert.setTitle(getString(R.string.app_name));
            alert.setMessage(getString(R.string.msg_no_flash));
            alert.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.btn_ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // finish();
                }
            });
            alert.show();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_audio_and_flash, container, false);

        // Initialize "Audio" button
        btnAudio = view.findViewById(R.id.btnAudio);
        isAudioOn = false;
        btnAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (isAudioOn) {
                        audioPlayer.stop();
                        btnAudio.setText(R.string.audio_on);
                        isAudioOn = false;
                    } else {
                        audioPlayer.play(getActivity(), R.raw.alarm, true);
                        btnAudio.setText(R.string.audio_off);
                        isAudioOn = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Initialize "Flash" button
        btnFlash = view.findViewById(R.id.btnFlash);
        isFlashOn = false;
        btnFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (isFlashOn) {
                        flashController.turnOff();
                        btnFlash.setText(R.string.flash_on);
                        isFlashOn = false;
                    } else {
                        flashController.turnOn();
                        btnFlash.setText(R.string.flash_off);
                        isFlashOn = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }
}
