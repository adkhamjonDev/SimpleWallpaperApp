package com.example.simplewallpaperapp.ui
import android.Manifest
import android.app.WallpaperManager
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.DownloadListener
import com.example.pagination.models.Hit
import com.example.simplewallpaperapp.MainActivity
import com.example.simplewallpaperapp.databinding.FragmentImageBinding
import com.example.simplewallpaperapp.room.AppDataBase
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.squareup.picasso.Picasso
import eightbitlab.com.blurview.BlurView
import eightbitlab.com.blurview.RenderScriptBlur
import java.io.File
import java.io.IOException
class ImageFragment : Fragment() {
    private lateinit var binding:FragmentImageBinding
    private lateinit var appDataBase: AppDataBase
    private var hit: Hit?=null
    private var number=-1
    private lateinit var file:File
    private lateinit var list:ArrayList<Hit>
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentImageBinding.inflate(inflater, container, false)
                (activity as MainActivity?)?.hide()
        appDataBase= AppDataBase.getInstance(requireContext())
        list= ArrayList(appDataBase.imageDao().getAllImages())
        setBlurs()
        if(arguments!=null){
            hit=arguments?.get("data")as Hit
            number=arguments?.get("num")as Int
        }
        for (i in 0 until list.size){
            if(list[i].id==hit?.id){
                binding.liked.visibility=View.GONE
                binding.unliked.visibility=View.VISIBLE
            }
        }
        Picasso.get().load(hit?.largeImageURL).into(binding.image)
        val dirPath="${Environment.getExternalStorageDirectory()}/DCIM"
        val fileName=hit?.id.toString()+".jpg"
        file= File(dirPath,fileName)
        AndroidNetworking.initialize(requireActivity())
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.share.setOnClickListener {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(
                Intent.EXTRA_TEXT,
                hit?.largeImageURL
            )
            sendIntent.type = "text/plain"
            startActivity(sendIntent)
        }
        binding.download.setOnClickListener {
            Dexter.withContext(context)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(object : PermissionListener {
                    @RequiresApi(Build.VERSION_CODES.R)
                    override fun onPermissionGranted(response: PermissionGrantedResponse) {
                        AndroidNetworking.download(hit?.largeImageURL, dirPath, fileName)
                            .build()
                            .startDownload(object : DownloadListener {
                                override fun onDownloadComplete() {
                                    Toast.makeText(requireContext(), "DownLoad Complete", Toast.LENGTH_SHORT)
                                        .show()
                                }

                                override fun onError(anError: ANError?) {
                                    Toast.makeText(requireContext(), anError?.message, Toast.LENGTH_SHORT)
                                        .show()
                                }
                            })
                    }
                    override fun onPermissionDenied(response: PermissionDeniedResponse) {
                        Toast.makeText(context, "Permission berilmadi", Toast.LENGTH_SHORT).show()
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permission: PermissionRequest?,
                        token: PermissionToken?
                    ) {
                        token?.continuePermissionRequest()
                    }
                }).check()
        }
        binding.wallpaper.setOnClickListener {
           binding.bars.visibility=View.GONE
            binding.share.visibility=View.GONE
           binding.wall.visibility=View.VISIBLE
           binding.back2.visibility=View.VISIBLE
        }
        binding.back2.setOnClickListener {
            binding.wall.visibility=View.GONE
            binding.back2.visibility=View.GONE
            binding.share.visibility=View.VISIBLE
            binding.bars.visibility=View.VISIBLE
            binding.back.visibility=View.VISIBLE
        }
        binding.homeWall.setOnClickListener {
            val bmpImg = (binding.image.drawable as BitmapDrawable).bitmap
            val wallManager = WallpaperManager.getInstance(context)
            try {
                wallManager.setBitmap(bmpImg,null, true,WallpaperManager.FLAG_SYSTEM)
                Toast.makeText(context, "Wallpaper Set Successfully!!", Toast.LENGTH_SHORT).show()

            } catch (e: IOException) {
                Toast.makeText(context, "Setting WallPaper Failed!!", Toast.LENGTH_SHORT).show()
            }
        }
        binding.lockWall.setOnClickListener {
            val bmpImg = (binding.image.drawable as BitmapDrawable).bitmap
            val wallManager = WallpaperManager.getInstance(context)
            try {
                wallManager.setBitmap(bmpImg,null, true,WallpaperManager.FLAG_LOCK)
                Toast.makeText(context, "Wallpaper Set Successfully!!", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                Toast.makeText(context, "Setting WallPaper Failed!!", Toast.LENGTH_SHORT).show()
            }
        }
        binding.liked.setOnClickListener {
            binding.liked.visibility=View.GONE
            binding.unliked.visibility=View.VISIBLE
            appDataBase.imageDao().addImage(hit!!)
        }
        binding.unliked.setOnClickListener {
            binding.liked.visibility=View.VISIBLE
            binding.unliked.visibility=View.GONE
            appDataBase.imageDao().removeImage(hit!!)
        }
        return binding.root
    }
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun setBlur(blurView: BlurView) {
        val radius = 10f
        val decorView = activity?.window?.decorView
        val rootView = decorView?.findViewById<View>(android.R.id.content) as ViewGroup
        val windowBackground = decorView.background
        blurView.setupWith(rootView)
            .setFrameClearDrawable(windowBackground)
            .setBlurAlgorithm(RenderScriptBlur(requireContext()))
            .setBlurRadius(radius)
            .setBlurAutoUpdate(true)
            .setHasFixedTransformationMatrix(true)
    }
    private fun setBlurs(){
        setBlur(binding.blurBack)
        setBlur(binding.blurBack2)
        setBlur(binding.blurShare)
        setBlur(binding.blurDownload)
        setBlur(binding.blurWallpaper)
        setBlur(binding.blurLike)
        setBlur(binding.blurWallpaperHome)
        setBlur(binding.blurWallpaperLock)
    }
    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity?)?.show()
    }
}

