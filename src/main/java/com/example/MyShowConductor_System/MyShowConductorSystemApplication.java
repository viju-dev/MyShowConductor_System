package com.example.MyShowConductor_System;

import com.example.MyShowConductor_System.Entities.*;
import com.example.MyShowConductor_System.EntryDTOs.ShowEntryDTO;
import com.example.MyShowConductor_System.Enums.*;
import com.example.MyShowConductor_System.Repositories.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

//import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@EnableSwagger2
@SpringBootApplication
public class MyShowConductorSystemApplication implements CommandLineRunner {//https://sl.bing.net/g94gQzp4Wg8

	private ModelMapper modelMapper;

	public static void main(String[] args) {
		SpringApplication.run(MyShowConductorSystemApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){

		return new ModelMapper();
	}

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private LanguageRepository languageRepository;

	@Autowired
	private FormatRepository formatRepository;

	@Autowired
	private GenreRepository genreRepository;

	@Autowired
	private TransactionStatusRepository statusRepository;

	Logger logger = LoggerFactory.getLogger(MyShowConductorSystemApplication.class);

	@Override
	public void run(String... args) throws Exception { // added in separate entities as mysql dont support arrays.
		try{
//			for roles
			for(UserRoleEnum role:UserRoleEnum.values()){ //  if (!roleRepository.existsById(role.ordinal() + 1)) {
				if(!roleRepository.existsById(role.ordinal() + 1)){
					Role role1 = new Role();
					role1.setName(role);
					role1.setId(role.ordinal()+1);
					roleRepository.save(role1);
				}
			}

//			for lanuages
			for (LanguagesEnum lang : LanguagesEnum.values()){
				 if(!languageRepository.existsById(lang.ordinal()+1)){
					Language language = new Language();
					language.setName(lang);
					language.setId(lang.ordinal()+1);
					languageRepository.save(language);
				 }
			}

//			for formats
			for(FormatEnum format:FormatEnum.values()) {
				if(!formatRepository.existsById(format.ordinal()+1)) {
					Format format1 = new Format();
					format1.setName(format);
					format1.setId(format.ordinal()+1);
					formatRepository.save(format1);
				}
			}

//			for genres
			for(MovieGenreEnum genre : MovieGenreEnum.values()){
				if(!genreRepository.existsById(genre.ordinal()+1)){
					Genre gen = new Genre();
					gen.setName(genre);
					gen.setId(genre.ordinal()+1);
					genreRepository.save(gen);
				}
			}

//			for transaction
			for(TransactionStatusEnum statusEnum:TransactionStatusEnum.values()) {
				if(!statusRepository.existsById(statusEnum.ordinal()+1)){
					TransactionStatus status = new TransactionStatus();
					status.setName(statusEnum);
					status.setId(statusEnum.ordinal()+1);
					statusRepository.save(status);
				}
			}
		}
		catch (Exception e){
			logger.error(e.getMessage(),e.getCause());
		}


//		System.out.println(this.passwordEncoder.encode("xyz"));
//		try {
//			Role role = new Role();
//			role.setId(AppConstants.ADMIN_USER);
//			role.setName("ADMIN_USER");//ROLE_ADMIN
//
//			Role role2 = new Role();
//			role2.setId(AppConstants.NORMAL_USER);
//			role2.setName("NORMAL_USER");//ROLE_NORMAL
//
//			List<Role> roles = List.of(role, role2);
//
//			List<Role> result = this.roleRepo.saveAll(roles);
//			result.forEach(r->{
//				System.out.println(r.getName());
//			});
//		}
//		catch (Exception e){
//			throw new RuntimeException(e.getMessage());
//		}
	}


}
