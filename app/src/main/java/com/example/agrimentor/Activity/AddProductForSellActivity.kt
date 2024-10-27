package com.example.agrimentor.Activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.agrimentor.R
import com.example.agrimentor.databinding.ActivityAddProductForSellBinding
import com.example.agrimentor.model.SellProductModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class AddProductForSellActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddProductForSellBinding
    lateinit var database : DatabaseReference
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_IMAGE_GALLERY = 2

    private val PICK_IMAGE_REQUEST = 71
    private lateinit var imageUri: Uri


    private lateinit var storageRef: StorageReference
    private lateinit var databaseRef: DatabaseReference

    var selectedCategory : String = ""
    var selectedProductName : String = ""
    var selectedRate : String = ""
    var selectedUnit : String = ""
    var selectedDescription : String = ""
    var selectedSellerName : String = ""
    var selectedSellerPhoneNumber : String = ""
    var selectedDivision : String = ""
    var selectedDistrict : String = ""
    var selectedThana : String = ""
    var selectedPhoto : String = ""




    // List of all divisions
    private val divisions = listOf("Division","Dhaka", "Chittagong", "Rajshahi", "Khulna", "Barisal", "Sylhet", "Rangpur", "Mymensingh")

    // Mapping each division to its districts
    private val districtMap = mapOf(
        "Dhaka" to listOf("District","Dhaka", "Gazipur", "Narayanganj", "Tangail", "Kishoreganj", "Manikganj", "Narsingdi", "Munshiganj", "Faridpur", "Gopalganj", "Madaripur", "Rajbari", "Shariatpur"),
        "Chittagong" to listOf("District","Chittagong", "Cox's Bazar", "Comilla", "Brahmanbaria", "Noakhali", "Feni", "Laxmipur", "Khagrachari", "Rangamati", "Bandarban"),
        "Rajshahi" to listOf("District","Rajshahi", "Pabna", "Natore", "Bogura", "Naogaon", "Joypurhat", "Sirajganj", "Chapainawabganj"),
        "Khulna" to listOf("District","Khulna", "Jessore", "Satkhira", "Narail", "Bagerhat", "Jhenaidah", "Magura", "Kushtia", "Chuadanga", "Meherpur"),
        "Barisal" to listOf("District","Barisal", "Patuakhali", "Bhola", "Pirojpur", "Jhalokathi", "Barguna"),
        "Sylhet" to listOf("District","Sylhet", "Moulvibazar", "Habiganj", "Sunamganj"),
        "Rangpur" to listOf("District","Rangpur", "Dinajpur", "Gaibandha", "Kurigram", "Lalmonirhat", "Nilphamari", "Panchagarh", "Thakurgaon"),
        "Mymensingh" to listOf("District","Mymensingh", "Netrokona", "Sherpur", "Jamalpur")
    )



    private val thanaMap = mapOf(
        // Dhaka Division
        "Dhaka" to listOf("Thana","Gulshan", "Banani", "Dhanmondi", "Mirpur", "Tejgaon", "Uttara", "Mohammadpur", "Badda", "Ramna", "Sabujbagh"),
        "Gazipur" to listOf("Thana","Joydebpur", "Kaliakair", "Tongi", "Kapasia", "Sreepur"),
        "Narayanganj" to listOf("Thana","Sonargaon", "Araihazar", "Rupganj", "Siddhirganj", "Bandar"),
        "Tangail" to listOf("Thana","Tangail Sadar", "Gopalpur", "Nagarpur", "Mirzapur", "Basail", "Dhanbari", "Ghatail", "Kalihati"),
        "Kishoreganj" to listOf("Thana","Kishoreganj Sadar", "Bajitpur", "Bhairab", "Hossainpur", "Itna", "Katiadi", "Karimganj"),
        "Manikganj" to listOf("Thana","Manikganj Sadar", "Singair", "Saturia", "Harirampur", "Ghior", "Shibalaya"),
        "Narsingdi" to listOf("Thana","Narsingdi Sadar", "Belabo", "Raipura", "Shibpur", "Monohardi", "Polash"),
        "Munshiganj" to listOf("Thana","Munshiganj Sadar", "Gazaria", "Sreenagar", "Lohajang", "Tongibari", "Sirajdikhan"),
        "Faridpur" to listOf("Thana","Faridpur Sadar", "Nagarkanda", "Bhanga", "Madhukhali", "Boalmari"),
        "Gopalganj" to listOf("Thana","Gopalganj Sadar", "Kotalipara", "Tungipara", "Muksudpur", "Kashiani"),
        "Madaripur" to listOf("Thana","Madaripur Sadar", "Rajoir", "Shibchar"),
        "Rajbari" to listOf("Thana","Rajbari Sadar", "Goalanda", "Pangsha", "Baliakandi"),
        "Shariatpur" to listOf("Thana","Shariatpur Sadar", "Damudya", "Naria", "Bhedarganj"),

        // Chittagong Division
        "Chittagong" to listOf("Thana","Pahartali", "Panchlaish", "Kotwali", "Chandgaon", "Raozan"),
        "Cox's Bazar" to listOf("Thana","Teknaf", "Ramu", "Maheshkhali", "Chakaria"),
        "Comilla" to listOf("Thana","Daudkandi", "Muradnagar", "Chandina", "Brahmanpara"),
        "Brahmanbaria" to listOf("Thana","Brahmanbaria Sadar", "Ashuganj", "Bancharampur", "Nabinagar"),
        "Noakhali" to listOf("Thana","Noakhali Sadar", "Begumganj", "Hatiya", "Senbagh"),
        "Feni" to listOf("Thana","Feni Sadar", "Chhagalnaiya", "Daganbhuiyan"),
        "Laxmipur" to listOf("Thana","Laxmipur Sadar", "Raipur", "Ramganj", "Ramgati"),
        "Khagrachari" to listOf("Thana","Khagrachari Sadar", "Dighinala", "Mahalchhari"),
        "Rangamati" to listOf("Thana","Rangamati Sadar", "Kaptai", "Baghaichhari"),
        "Bandarban" to listOf("Thana","Bandarban Sadar", "Thanchi", "Ruma", "Rowangchhari"),

        // Rajshahi Division
        "Rajshahi" to listOf("Thana","Boalia", "Motihar", "Rajpara", "Shah Makhdum"),
        "Pabna" to listOf("Thana","Santhia", "Ishwardi", "Sujanagar", "Bera"),
        "Natore" to listOf("Thana","Bagatipara", "Baraigram", "Naldanga", "Lalpur"),
        "Bogura" to listOf("Thana","Bogura Sadar", "Shajahanpur", "Dupchanchia"),
        "Naogaon" to listOf("Thana","Naogaon Sadar", "Raninagar", "Manda"),
        "Joypurhat" to listOf("Thana","Joypurhat Sadar", "Panchbibi", "Khetlal"),
        "Sirajganj" to listOf("Thana","Sirajganj Sadar", "Kazipur", "Tarash", "Shahjadpur"),
        "Chapainawabganj" to listOf("Thana","Chapainawabganj Sadar", "Nachole", "Shibganj"),

        // Khulna Division
        "Khulna" to listOf("Thana","Khulna Sadar", "Sonadanga", "Dumuria"),
        "Jessore" to listOf("Thana","Jessore Sadar", "Bagherpara", "Abhaynagar"),
        "Satkhira" to listOf("Thana","Satkhira Sadar", "Tala", "Debhata"),
        "Narail" to listOf("Thana","Narail Sadar", "Kalia", "Lohagara"),
        "Bagerhat" to listOf("Thana","Bagerhat Sadar", "Rampal", "Mongla"),
        "Jhenaidah" to listOf("Thana","Jhenaidah Sadar", "Kotchandpur", "Maheshpur"),
        "Magura" to listOf("Thana","Magura Sadar", "Mohammadpur", "Shalikha"),
        "Kushtia" to listOf("Thana","Kushtia Sadar", "Bheramara", "Mirpur"),
        "Chuadanga" to listOf("Thana","Chuadanga Sadar", "Alamdanga", "Damurhuda"),
        "Meherpur" to listOf("Thana","Meherpur Sadar", "Gangni"),

        // Barisal Division
        "Barisal" to listOf("Thana","Barisal Sadar", "Bakerganj", "Babuganj"),
        "Patuakhali" to listOf("Thana","Patuakhali Sadar", "Bauphal", "Kalapara"),
        "Bhola" to listOf("Thana","Bhola Sadar", "Char Fasson", "Lalmohan"),
        "Pirojpur" to listOf("Thana","Pirojpur Sadar", "Bhandaria", "Mathbaria"),
        "Jhalokathi" to listOf("Thana","Jhalokathi Sadar", "Kathalia", "Rajapur"),
        "Barguna" to listOf("Thana","Barguna Sadar", "Patharghata", "Amtali"),

        // Sylhet Division
        "Sylhet" to listOf("Thana","Sylhet Sadar", "Beanibazar", "Zakiganj"),
        "Moulvibazar" to listOf("Thana","Moulvibazar Sadar", "Srimangal", "Kamalganj"),
        "Habiganj" to listOf("Thana","Habiganj Sadar", "Chunarughat", "Bahubal"),
        "Sunamganj" to listOf("Thana","Sunamganj Sadar", "Tahirpur", "Bishwamvarpur"),

        // Rangpur Division
        "Rangpur" to listOf("Thana","Rangpur Sadar", "Pirganj", "Badarganj"),
        "Dinajpur" to listOf("Thana","Dinajpur Sadar", "Parbatipur", "Birganj"),
        "Gaibandha" to listOf("Thana","Gaibandha Sadar", "Palashbari", "Gobindaganj"),
        "Kurigram" to listOf("Thana","Kurigram Sadar", "Nageshwari", "Bhurungamari"),
        "Lalmonirhat" to listOf("Thana","Lalmonirhat Sadar", "Hatibandha", "Kaliganj"),
        "Nilphamari" to listOf("Thana","Nilphamari Sadar", "Saidpur", "Jaldhaka"),
        "Panchagarh" to listOf("Thana","Panchagarh Sadar", "Tetulia", "Boda"),
        "Thakurgaon" to listOf("Thana","Thakurgaon Sadar", "Pirganj", "Baliadangi"),

        // Mymensingh Division
        "Mymensingh" to listOf("Thana","Mymensingh Sadar", "Trishal", "Muktagachha"),
        "Netrokona" to listOf("Thana","Netrokona Sadar", "Purbadhala", "Kendua"),
        "Sherpur" to listOf("Thana","Sherpur Sadar", "Nakla", "Nalitabari"),
        "Jamalpur" to listOf("Thana","Jamalpur Sadar", "Sarishabari", "Islampur")
    )


    private val categoryMap = mapOf(
        "Fruit" to listOf("Apple", "Banana", "Mango", "Orange"),
        "Vegetables" to listOf("Carrot", "Potato", "Tomato", "Onion"),
        "Seeds" to listOf("Wheat", "Rice", "Corn", "Sunflower")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductForSellBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backbutton.setOnClickListener { finish() }
        database = FirebaseDatabase.getInstance().getReference("SellProduct")
        storageRef = FirebaseStorage.getInstance().reference

        // Set up division spinner with list of divisions
        val divisionAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, divisions)
        divisionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.division.adapter = divisionAdapter

        // Listen for division selection to load districts
        binding.division.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedDivision2 = divisions[position]
                loadDistricts(selectedDivision2)

                selectedDivision = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // Listen for district selection to load thanas
        binding.district.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedDistrict2 = binding.district.selectedItem as? String
                selectedDistrict2?.let { loadThanas(it) }
                selectedDistrict = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }


        // Set up the onItemSelectedListener
        binding.thana.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedThana = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do something when nothing is selected (optional)
            }
        }


        // Set up category spinner
        val categories = categoryMap.keys.toList()
        val categoryAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.category.adapter = categoryAdapter

        // Set up onItemSelectedListener for category spinner
        binding.category.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedCategory2 = categories[position]
                loadProducts(selectedCategory2)
                selectedCategory = parent.getItemAtPosition(position).toString()

            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }



        // Set up the onItemSelectedListener
        binding.productname.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedProductName = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do something when nothing is selected (optional)
            }
        }




        // Set up unit spinner with predefined units
        val units = listOf("piece", "Kg", "Sack")
        val unitAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, units)
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.unit.adapter = unitAdapter



        // Set up the onItemSelectedListener
        binding.unit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedUnit = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do something when nothing is selected (optional)
            }
        }



        binding.btnpublish.setOnClickListener{
            selectedRate = binding.rate.text.toString()
            selectedDescription = binding.description.text.toString()
            selectedSellerName = binding.sellerName.text.toString()
            selectedSellerPhoneNumber = binding.sellerPhoneNumber.text.toString()

            publish()

        }


        binding.productphoto.setOnClickListener {
            showImagePickerOptions()
        }

    }

    private fun publish() {
        var id:String = database.push()!!.key!!
        var sellProductModel = SellProductModel(id,selectedCategory,selectedProductName,selectedRate,selectedUnit,selectedDescription,selectedSellerName,selectedSellerPhoneNumber,selectedDivision,selectedDistrict,selectedThana)

        database.child(id).setValue(sellProductModel)
            .addOnSuccessListener {
                Toast.makeText(this,"Product Publish Successfully",Toast.LENGTH_LONG).show()
                finish()
            }

    }


    private fun showImagePickerOptions() {
        val options = arrayOf("Take Photo", "Choose from Gallery")
        val builder = android.app.AlertDialog.Builder(this)
        builder.setItems(options) { dialog, which ->
            when (which) {
                0 -> openCamera()
                1 -> openGallery()
            }
        }
        builder.show()
    }

    private fun openCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_IMAGE_CAPTURE)
        } else {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, REQUEST_IMAGE_GALLERY)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    binding.productphoto.setImageBitmap(imageBitmap)
                }
                REQUEST_IMAGE_GALLERY -> {
                    val selectedImageUri: Uri? = data?.data
                    binding.productphoto.setImageURI(selectedImageUri)
                }
            }
        }
    }




    // Function to load products based on selected category
    private fun loadProducts(category: String) {
        val products = categoryMap[category] ?: listOf()
        val productAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, products)
        productAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.productname.adapter = productAdapter
    }


    // Function to load districts based on the selected division
    private fun loadDistricts(division: String) {
        val districts = districtMap[division] ?: listOf()
        val districtAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, districts)
        districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.district.adapter = districtAdapter

        // Reset Thana Spinner
        // binding.thana.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listOf())
    }

    // Function to load thanas based on the selected district
    private fun loadThanas(district: String) {
        val thanas = thanaMap[district] ?: listOf()
        val thanaAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, thanas)
        thanaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.thana.adapter = thanaAdapter
    }
}
