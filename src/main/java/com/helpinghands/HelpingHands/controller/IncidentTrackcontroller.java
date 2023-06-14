package com.helpinghands.HelpingHands.controller;

import com.helpinghands.HelpingHands.entities.*;
import com.helpinghands.HelpingHands.exception.EmptyListException;
import com.helpinghands.HelpingHands.repository.AdminDao;
import com.helpinghands.HelpingHands.repository.Centralrepositoryofincidentdao;
import com.helpinghands.HelpingHands.repository.Locationdao;
import com.helpinghands.HelpingHands.repository.Temporarydatabaseofincidentdao;
import com.helpinghands.HelpingHands.services.Incidenttrackservice;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@Log4j2
public class IncidentTrackcontroller {
    @Autowired
    private Incidenttrackservice incidenttrackservice;

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private Temporarydatabaseofincidentdao temporarydatabaseofincidentdao;
    @Autowired
    private Centralrepositoryofincidentdao centralrepositoryofincidentdao;

    @Autowired
    private Locationdao locationdao;
    @GetMapping("/getallactiveincident")
    public List<Temporarydatabaseofincident> getAllactiveincident() throws EmptyListException {
        return this.incidenttrackservice.getAllActiveIncident();
    }
    @GetMapping("/totalcasualitybyincident/{incidentid}")
    public long totalcasualitybyincident(@PathVariable String incidentid){
        return this.incidenttrackservice.totalCasualityByIncident(incidentid);
    }

    @GetMapping("/getallincidentsofcountry")
    public List<Centralrepositoryofincident> getallincidentofcountry() throws EmptyListException {
        return this.incidenttrackservice.getAllIncidentHappens();
    }
    @PostMapping("/addlocal")
    public Location addincident(@RequestBody @Valid Temporarydatabaseofincident incident, @RequestHeader String userId) throws MethodArgumentNotValidException,IllegalStateException {
       return  this.incidenttrackservice.reportTheIncident(incident,userId);
    }

    @GetMapping("/verifyincident")
    public String incidentverifybyadmin(@RequestHeader String incidentId) throws NoSuchElementException {
        this.incidenttrackservice.incidentVerificationByAdmin(incidentId);
        return "incident verified by admin";
    }
    @GetMapping("/getadmin")
    public Admin getadmin(@RequestHeader String postalcode) throws NoSuchElementException{
        return this.incidenttrackservice.getAdminOfArea(postalcode);
    }
    @DeleteMapping("/setincidentenddate")
    public Centralrepositoryofincident endincident(@RequestHeader String incidentid) throws NoSuchElementException{
        Temporarydatabaseofincident incident= temporarydatabaseofincidentdao.findById(incidentid).get();

        if(incident!=null) {
            Location location = locationdao.findById(getPostalByIncidentId(incidentid)).get();
            Users user = getUserByIncidentInLocal(incidentid);
            List<Centralrepositoryofincident> centralrepositoryofincidents= location.getCentralrepositoryofincidentList();
            List<Centralrepositoryofincident> centralrepositoryofincidentList= user.getCentralrepositoryofincidents();
            List<Temporarydatabaseofincident> temporarydatabaseofincidents= location.getTemporarydatabaseofincidents();
            temporarydatabaseofincidents.remove(incident);
            List<Temporarydatabaseofincident> temporarydatabaseofincidents1= user.getTemporarydatabaseofincidents();
            temporarydatabaseofincidents1.remove(incident);
            temporarydatabaseofincidentdao.delete(incident);
            Centralrepositoryofincident centralrepositoryofincident= new Centralrepositoryofincident();

            centralrepositoryofincident.setId(incident.id);
            centralrepositoryofincident.setName(incident.getName());
            centralrepositoryofincident.setIncidentEndDate(incident.getIncidenteffectdate());
            centralrepositoryofincident.setDistrict(incident.getDistrict());
            centralrepositoryofincident.setCasualty(incident.getCasualty());
            centralrepositoryofincident.setDescription(incident.getDescription());
            centralrepositoryofincident.setIncidentDate(incident.getIncidentDate());
            centralrepositoryofincident.setState(incident.getState());
            centralrepositoryofincidentdao.save(centralrepositoryofincident);

            centralrepositoryofincidentList.add(centralrepositoryofincident);
            centralrepositoryofincidents.add(centralrepositoryofincident);
            locationdao.save(location);

            return centralrepositoryofincident;

      }
       else throw new NoSuchElementException("No INcident Found with id");
        //return this.incidenttrackservice.incidentEnd(incidentid);

    }
    @GetMapping("/getallincidentofarea")
    public List<Centralrepositoryofincident> getallincincidentofarea(@RequestHeader String postalcode) throws EmptyListException{
        return this.incidenttrackservice.findAllIncidentInArea(postalcode);
    }
    @GetMapping("/findtotalincidentapprovebyadmin")
    public List<Object> findtotalincidentapprovebyadmin(@RequestHeader String adminId) throws NoSuchElementException,EmptyListException{
        return this.incidenttrackservice.findTotalIncidentApproveByAdmin(adminId);
    }

    @GetMapping("/getpostalbyuser/{userId}")
    public String getpostalbyuser(@PathVariable String userId){
        return incidenttrackservice.getPostalByUserId(userId);
    }

    @GetMapping("/getPostalByIncidentId/{incidentid}")
    public String getPostalByIncidentId(@PathVariable String incidentid){
        return this.incidenttrackservice.getPostalByIncidentId(incidentid);
    }

    @GetMapping("/getpostalbyadminid")
    public String getpostalbyadminid(@RequestHeader String adminid){
        return this.incidenttrackservice.getPostalByAdminId(adminid);
    }
    @GetMapping("/overallcasualitiesinarea/{postal}")
    public long overallcasualityinarea(@PathVariable String postal){
        return this.incidenttrackservice.overallCasualitiesByIncidentsInArea(postal);
    }
    @GetMapping("/findallincidentraisebyuser")
    public List<Object> findallincidentraisebyuser(@RequestHeader String userid) throws EmptyListException{
        return  this.incidenttrackservice.findAllIncidentRaiseByUser(userid);
    }
    @GetMapping("/getadminbyincident")
    public Admin getadminbyincident(@RequestHeader String incidentid){
        return this.incidenttrackservice.getAdminByIncident(incidentid);
    }
    @GetMapping("/getuserbyincident")
    public Users getUserByIncident(@RequestHeader String incidentid){
        return this.incidenttrackservice.getUserByIncident(incidentid);

    }

    public Users getUserByIncidentInLocal(String id){
        return this.incidenttrackservice.getUserByIncidentInLocal(id);
    }

}
