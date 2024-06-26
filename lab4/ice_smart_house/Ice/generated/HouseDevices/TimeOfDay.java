//
// Copyright (c) ZeroC, Inc. All rights reserved.
//
//
// Ice version 3.7.10
//
// <auto-generated>
//
// Generated from file `smarthouse.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package HouseDevices;

public class TimeOfDay extends com.zeroc.Ice.Value
{
    public TimeOfDay()
    {
    }

    public TimeOfDay(short hour, short minute, short second)
    {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public short hour;

    public short minute;

    public short second;

    public TimeOfDay clone()
    {
        return (TimeOfDay)super.clone();
    }

    public static String ice_staticId()
    {
        return "::HouseDevices::TimeOfDay";
    }

    @Override
    public String ice_id()
    {
        return ice_staticId();
    }

    /** @hidden */
    public static final long serialVersionUID = 620830247L;

    /** @hidden */
    @Override
    protected void _iceWriteImpl(com.zeroc.Ice.OutputStream ostr_)
    {
        ostr_.startSlice(ice_staticId(), -1, true);
        ostr_.writeShort(hour);
        ostr_.writeShort(minute);
        ostr_.writeShort(second);
        ostr_.endSlice();
    }

    /** @hidden */
    @Override
    protected void _iceReadImpl(com.zeroc.Ice.InputStream istr_)
    {
        istr_.startSlice();
        hour = istr_.readShort();
        minute = istr_.readShort();
        second = istr_.readShort();
        istr_.endSlice();
    }
}
