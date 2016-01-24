package pl.lodz.p.ftims.pai.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@ConfigurationProperties(prefix="environment")
@Component
public class EnvironmentConfiguration {

    @NotNull
    private Headquarters headquarters;

    @NotNull
    private Department department;

    public static class Headquarters {

        @NotNull
        private String synchronizationEndpoint;

        @NotNull
        private String domain;

        @NotNull
        private String port;

        public void setSynchronizationEndpoint(String synchronizationEndpoint) {
            this.synchronizationEndpoint = synchronizationEndpoint;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public void setPort(String port) {
            this.port = port;
        }
    }

    public static class Department {

        @NotNull
        private Integer id;

        public void setId(Integer id) {
            this.id = id;
        }
    }

    public Integer getDepartmentId(){
        return department.id;
    }

    public String getHeadquartersSynchronizationEndpoint(){
        return headquarters.synchronizationEndpoint;
    }

    public String getHeadquartersDomain(){
        return headquarters.domain;
    }

    public String getHeadquartersPort(){
        return headquarters.port;
    }

    public Headquarters getHeadquarters() {
        return headquarters;
    }

    public Department getDepartment() {
        return department;
    }

    public void setHeadquarters(Headquarters headquarters) {
        this.headquarters = headquarters;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
