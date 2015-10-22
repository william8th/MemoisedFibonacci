/**
 * Created by William Heng(dev) on 22/10/15.
 */
public interface KVCache {
    public void set(String key, String value);
    public String get(String key);
    public boolean exists(String key);
}
