package org.immuni.android.ui.home.family.details.edit

import android.os.Bundle
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import com.bendingspoons.base.extensions.setLightStatusBarFullscreen
import com.bendingspoons.base.utils.ScreenUtils
import kotlinx.android.synthetic.main.user_edit_age_group_activity.*
import org.immuni.android.ImmuniActivity
import org.immuni.android.R
import org.immuni.android.loading
import org.immuni.android.models.AgeGroup
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

class EditAgeGroupActivity : BaseEditActivity() {
    private lateinit var viewModel: EditDetailsViewModel
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_edit_age_group_activity)
        userId = intent?.extras?.getString("userId")!!
        viewModel = getViewModel { parametersOf(userId)}

        viewModel.navigateBack.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                finish()
            }
        })

        viewModel.user.observe(this, Observer {
            age_range_0_17.isChecked = false
            age_range_18_35.isChecked = false
            age_range_36_45.isChecked = false
            age_range_46_55.isChecked = false
            age_range_56_65.isChecked = false
            age_range_66_75.isChecked = false
            age_range_75.isChecked = false

            when(it.ageGroup) {
                AgeGroup.ZERO_SEVENTEEN -> age_range_0_17.isChecked = true
                AgeGroup.EIGHTEEN_THIRTYFIVE -> age_range_18_35.isChecked = true
                AgeGroup.THRITYSIX_FORTYFIVE -> age_range_36_45.isChecked = true
                AgeGroup.FORTYSIX_FIFTYFIVE -> age_range_46_55.isChecked = true
                AgeGroup.FIFTYSIX_SIXTYFIVE -> age_range_56_65.isChecked = true
                AgeGroup.SIXTYSIX_SEVENTYFIVE -> age_range_66_75.isChecked = true
                AgeGroup.MORE_THAN_SEVENTYFIVE -> age_range_75.isChecked = true
            }

            pageTitle.text = when(it.isMain) {
                true -> applicationContext.getString(R.string.onboarding_age_title)
                false -> String.format(applicationContext.getString(R.string.user_edit_age_you_title),
                    it.nickname!!.humanReadable(applicationContext, it.gender))
            }
        })

        viewModel.loading.observe(this, Observer {
            loading(it)
        })

        back.setOnClickListener { finish() }

        update.setOnClickListener {
            val ageGroup = when {
                age_range_0_17.isChecked -> AgeGroup.ZERO_SEVENTEEN
                age_range_18_35.isChecked -> AgeGroup.EIGHTEEN_THIRTYFIVE
                age_range_36_45.isChecked -> AgeGroup.THRITYSIX_FORTYFIVE
                age_range_46_55.isChecked -> AgeGroup.FORTYSIX_FIFTYFIVE
                age_range_56_65.isChecked -> AgeGroup.FIFTYSIX_SIXTYFIVE
                age_range_66_75.isChecked -> AgeGroup.SIXTYSIX_SEVENTYFIVE
                else -> AgeGroup.MORE_THAN_SEVENTYFIVE
            }

            val user = viewModel.user()
            user?.let {
                viewModel.updateUser(user.copy(ageGroup = ageGroup))
            }
        }
    }
}
