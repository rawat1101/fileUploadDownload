package in.til.tp.core.utils;

import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

@UtilityClass
public class CRCGenerator {
    private static final long POLY64REV = 0xd800000000000000L;

    private static final long[] LOOKUPTABLE;

    static {
        LOOKUPTABLE = new long[0x100];
        for (int i = 0; i < 0x100; i++) {
            long v = i;
            for (int j = 0; j < 8; j++) {
                if ((v & 1) == 1) {
                    v = (v >>> 1) ^ POLY64REV;
                } else {
                    v = (v >>> 1);
                }
            }
            LOOKUPTABLE[i] = v;
        }
    }

    /**
     * Calculates the CRC64 checksum for the given data array.
     *
     * @return checksum value
     */
    private static long getCode(String str) {
        byte[] data = str.getBytes();
        long sum = 0;
        for (int i = 0; i < data.length; i++) {
            final int lookupidx = ((int) sum ^ data[i]) & 0xff;
            sum = (sum >>> 8) ^ LOOKUPTABLE[lookupidx];
        }
        return sum;
    }


    public static long getCode32(String str) {
        byte[] bytes = str.getBytes();
        Checksum checksum = new CRC32();
        checksum.update(bytes, 0, bytes.length);
        long val = checksum.getValue();
        return val;
    }

    /**
     * @param channelName
     * @return crc 64 bit long code
     */
    public static long getChannelCode(String channelName) {

        return getCode(channelName);

    }

    public static long getCRCCode(String str) {

        return getCode(str);

    }

    /**
     * @param siteCode
     * @param ChannelName
     * @return
     */
    public static long getSubChannelCode(String siteCode, String ChannelName) {

        return getCode(siteCode + "-" + ChannelName);

    }

    /**
     * @param domain
     * @param siteCode
     * @param ChannelName
     * @return
     */
    public static long getDomainSubChannelCode(String domain, String siteCode, String ChannelName) {
        return getCode(domain + "-" + siteCode + "-" + ChannelName);
    }

    /**
     * @param ChannelName
     * @param ActivityName
     * @return
     */
    public static long getChannelActivityCode(String ChannelName, String ActivityName) {

        return getCode(ChannelName + "-" + ActivityName);
    }

    /**
     * @param SubChannelName
     * @param ChannelName
     * @param ActivityName
     * @return
     */
    public static long getSubChannelActivityCode(String SubChannelName, String ChannelName, String ActivityName) {

        return getCode(SubChannelName + "-" + ChannelName + "-" + ActivityName);

    }

    public static long getUserCode(String userId) {
        return getCode(userId);
    }

    public static Map<Long, String> getUserCodes(String[] userIds) {
        Map<Long, String> userCode = new HashMap<>();
        for (int counter = 0; counter < userIds.length; counter++) {
            userCode.put(getUserCode(userIds[counter]), userIds[counter]);
        }
        return userCode;
    }

    public static long getChannelUserCode(String ChannelName, String userId) {
        return getCode(ChannelName + "-" + userId);
    }

    public static long getPUCode(String property, String hexId) {
        return getCode(property + "-" + hexId);
    }

    public static long getBadgeFamilyId(String channelName, String tpBadgeName) {
        return getCode(channelName + "-" + tpBadgeName);
    }

    public static long generateBillNumber(String propertyName, String monthYear) {
        return getCode(propertyName + "-" + monthYear);
    }

    public static long getPaCode(String publisherName, String name) {
        return getCode(publisherName + "-" + name);
    }

    public static long getSpaCode(String sname, String pname, String activityName) {
        return getCode(sname + "-" + pname + "-" + activityName);
    }

    public static long getSpCode(String sname, String pname) {
        return getCode(sname + "-" + pname);
    }

    public static long getSaCode(String pname, String activityName) {
        return getCode(pname + "-" + activityName);
    }

    public static long getPAUcode(String pname, String aname, String ssoid) {
        long paucode = CRCGenerator.getCode(pname + "-" + aname + "-" + ssoid);
        return paucode;
    }

    public static void main(String[] args) {
        System.out.println(getCode("13.233.220.0"));
    }
}
