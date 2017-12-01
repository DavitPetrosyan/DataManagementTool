package com.davit.ymlparser.endpoint;

import com.davit.ymlparser.service.DataManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author david.petrosyan@synisys.com on 11/30/17.
 */
@RestController
@RequestMapping("/datamanager")
@CrossOrigin(origins = "*")
public class Controller {

	private DataManagementService dataManagementService;

	@Autowired
	public Controller(DataManagementService dataManagementService) {
		this.dataManagementService = dataManagementService;
	}

	@GetMapping("/h2test")
	@ResponseStatus(value = HttpStatus.OK)
	public void test() {
		dataManagementService.syncData();


		/*DeleteDbFiles.execute("~", "test", true);
		DataManagementDaoImpl.insertWithStatement();
		DeleteDbFiles.execute("~", "test", true);
		DataManagementDaoImpl.insertWithPreparedStatement();*/
	}
}
