package kr.loplab.gnss05.common

class Utilities {
    companion object{
    public fun doubledegree(doubleValue : Double) : Double{
        var dd = Math.floor(doubleValue);
        var mm = Math.floor((doubleValue - dd) / 0.01);
        var ssssss = Math.floor((doubleValue - dd - 0.01*mm ) * 100000000);
        var mmssssss = mm + (ssssss/600000).toInt() + ((ssssss%600000)/1000000)
        var ddmmssssss = dd + (mmssssss/60).toInt() + (mmssssss%60/100)
        return ddmmssssss
    }
    }
}