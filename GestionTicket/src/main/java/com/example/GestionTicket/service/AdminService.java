package com.example.GestionTicket.service;

import com.example.GestionTicket.entity.Admin;
import com.example.GestionTicket.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

   @Autowired
    private AdminRepository adminRepository;
   public List<Admin> getAllAdmins(){
       return adminRepository.findAll();
   }
   public Optional<Admin> getAdminById(Long id){
       return adminRepository.findById(id);
   }
   //add
    public Admin createAdmin(Admin admin){
       return adminRepository.save(admin);
    }
    //update
    public Admin updateAdmin(Long id,Admin admin){
       Optional<Admin> adminOptional = adminRepository.findById(id);
       if(adminOptional.isPresent()){
           Admin adminToUpdate = adminOptional.get();
           adminToUpdate.setApprenants(admin.getApprenants());
           adminToUpdate.setEmail(admin.getEmail());
           adminToUpdate.setPrenom(admin.getPrenom());
           adminToUpdate.setNom(admin.getNom());
           adminToUpdate.setRole(admin.getRole());
           adminToUpdate.setFormateurs(admin.getFormateurs());
           adminToUpdate.setMotDePasse(admin.getMotDePasse());
           return adminRepository.save(adminToUpdate);
       }
       else{
           throw new RuntimeException("Admin not found");
       }

    }
    //supprimer
    public void deleteAdmin(Long id){
       adminRepository.deleteById(id);
    }
}
