public class WeatherInfo {
    private String id;
    private String name;
    private String state;
    private String time_zone;
    private String full_time;
    private String air_temp;
    private String apparent_t;
    private String cloud;

    public WeatherInfo(String id, String name, String state, String time_zone, String local_date_time_full,
                       String air_temp, String apparent_t, String cloud) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.time_zone = time_zone;
        this.full_time = local_date_time_full;
        this.air_temp = air_temp;
        this.apparent_t = apparent_t;
        this.cloud = cloud;
    }

    public String get_id() { return id; }
    public String get_name() { return name; }
    public String get_state() { return state; }
    public String get_time_zone() { return time_zone; }

    public String full_time() { return full_time; }
    public String get_air_temp() { return air_temp; }
    public String get_apparent_t() { return apparent_t; }
    public String get_cloud() { return cloud; }

    public void set_id(String id) { this.id = id; }
    public void set_name(String name) { this.name = name; }
    public void set_state(String state) { this.state = state; }
    public void set_time_zone(String time_zone) { this.time_zone = time_zone; }
    public void set_full_time(String local_date_time_full) { this.full_time = local_date_time_full; }
    public void set_air_temp(String air_temp) { this.air_temp = air_temp; }
    public void set_apparent_t(String apparent_t) { this.apparent_t = apparent_t; }
    public void set_cloud(String cloud) { this.cloud = cloud; }


    public boolean isValid()
    {
        if(this.id == null) return false;
        if(this.name == null) return false;
        if(this.state == null) return false;
        if(this.time_zone == null) return false;
        if(this.full_time == null) return false;
        if(this.air_temp == null) return false;
        if(this.apparent_t == null) return false;
        if(this.cloud == null) return false;
        return true;
    }

}
