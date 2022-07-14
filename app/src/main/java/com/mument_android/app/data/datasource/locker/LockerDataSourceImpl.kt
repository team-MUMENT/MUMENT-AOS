package com.mument_android.app.data.datasource.locker

import com.mument_android.app.data.dto.MumentCard
import com.mument_android.app.data.dto.ResponseMyMumentListDto
import com.mument_android.app.data.dto.UserDto
import com.mument_android.app.data.network.locker.LockerNetwork
import com.mument_android.app.domain.entity.MumentCardData.Music
import com.mument_android.app.domain.entity.MumentCardData.User
import javax.inject.Inject

class LockerDataSourceImpl @Inject constructor(
    private val lockerNetwork: LockerNetwork
): LockerDataSource {
    override suspend fun fetchLockerMumumentList(): ResponseMyMumentListDto {
        return ResponseMyMumentListDto(
            UserDto("id", "https://img3.yna.co.kr/etc/inner/KR/2022/03/20/AKR20220320029100005_01_i_P4.jpg", "이수지"),
            listOf(
                MumentCard(
                    "213", "음악은 저에게 영감을 줘요, 이 곡 추천해준 이부장에게 심심한 감사의 인사를 음악은 저에게 영감을 줘요. 추천해준 이부장에게 심심한 감사의 인사를 음악은 저에게",
                    "1 Sep, 2020",
                    Music(
                        "213",
                        "백예린",
                        "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
                        "Antifreeze"
                    ),
                    User(
                        "123",
                        "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
                        "이수지"
                    ),
                    null, null, null, null, null, "2020", "1"
                ),
                MumentCard(
                    "213", "음악은 저에게 영감을 줘요, 이 곡 추천해준 이부장에게 심심한 감사의 인사를 음악은 저에게 영감을 줘요. 추천해준 이부장에게 심심한 감사의 인사를 음악은 저에게",
                    "1 Sep, 2020",
                    Music(
                        "213",
                        "백예린",
                        "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
                        "Antifreeze"
                    ),
                    User(
                        "123",
                        "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
                        "이수지"
                    ),
                    null, null, null, null, null, "2020", "1"
                ),
                MumentCard(
                    "213", "음악은 저에게 영감을 줘요, 이 곡 추천해준 이부장에게 심심한 감사의 인사를 음악은 저에게 영감을 줘요. 추천해준 이부장에게 심심한 감사의 인사를 음악은 저에게",
                    "1 Sep, 2020",
                    Music(
                        "213",
                        "백예린",
                        "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
                        "Antifreeze"
                    ),
                    User(
                        "123",
                        "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
                        "이수지"
                    ),
                    null, null, null, null, null,  "2020", "1"
                ),
                MumentCard(
                    "213", "음악은 저에게 영감을 줘요, 이 곡 추천해준 이부장에게 심심한 감사의 인사를 음악은 저에게 영감을 줘요. 추천해준 이부장에게 심심한 감사의 인사를 음악은 저에게",
                    "1 Sep, 2020",
                    Music(
                        "213",
                        "백예린",
                        "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
                        "Antifreeze"
                    ),
                    User(
                        "123",
                        "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
                        "이수지"
                    ),
                    null, null, null, null, null, "2020", "2"
                )

            )
        )
    }
//       = lockerNetwork.fetchLockerMumentList()
}