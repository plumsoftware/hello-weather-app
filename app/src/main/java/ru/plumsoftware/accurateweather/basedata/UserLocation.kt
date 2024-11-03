package ru.plumsoftware.accurateweather.basedata

import android.os.Parcel
import android.os.Parcelable

class UserLocation(
    lat: Double?,
    long: Double?,
    _cityName: String?,
    _countryName: String?,
    _countryCode: String?
) : Parcelable {
    constructor(
        parcel: Parcel
    ) : this(
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    {
        latitude = parcel.readValue(kotlin.Double::class.java.classLoader) as? Double
        longitude = parcel.readValue(kotlin.Double::class.java.classLoader) as? Double
        cityName = parcel.readString()
        countryName = parcel.readString()
        countryCode = parcel.readString()
    }

    var latitude: Double? = lat
    var longitude: Double? = long
    var cityName: String? = _cityName
    var countryName: String? = _countryName
    var countryCode: String? = _countryCode

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, p1: Int) {
        parcel.writeDouble(latitude!!)
        parcel.writeDouble(longitude!!)
        parcel.writeString(cityName!!)
        parcel.writeString(countryName!!)
        parcel.writeString(countryCode!!)
    }

    companion object CREATOR : Parcelable.Creator<UserLocation> {
        override fun createFromParcel(parcel: Parcel): UserLocation {
            return UserLocation(parcel)
        }

        override fun newArray(size: Int): Array<UserLocation?> {
            return arrayOfNulls(size)
        }
    }


}