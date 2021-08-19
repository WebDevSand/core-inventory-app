package com.ims.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ims.beans.Department;
import com.ims.beans.Discount;
import com.ims.beans.InventoryItem;
import com.ims.dtos.InventoryItemDto;
import com.ims.dtos.LineItemDto;
import com.ims.services.DepartmentService;
import com.ims.services.DiscountService;
import com.ims.services.InventoryItemService;

@RestController
@RequestMapping(value="/inventoryitem")
public class InventoryItemController {

	static String bucketname = "lawmas";
	@Autowired
	private InventoryItemService inventoryItemService;
	@Autowired
	private DepartmentService dService;
	@Autowired
	private DiscountService discountService;
	public void setInventoryItemService(InventoryItemService inventoryItemService) {
		this.inventoryItemService = inventoryItemService;
	}
	public void setdService(DepartmentService dService) {
		this.dService = dService;
	}
	public void setDiscountService(DiscountService discountService) {
		this.discountService = discountService;
	}

	@RequestMapping(value="/create",method=(RequestMethod.POST),
			consumes=(MediaType.APPLICATION_JSON_VALUE),
			produces=(MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<InventoryItemDto> addItem(@RequestBody InventoryItemDto iDto){
		Department d = dService.getById(iDto.getDepartmentid());
		Discount di = null;
		System.out.println(iDto.getDiscountid());
		if(iDto.getDiscountid() != -1) {
			di = discountService.getById(iDto.getDiscountid());
			System.out.println(di);
			iDto.setDiscountid(di.getDiscountID());
			System.out.println(iDto.getDiscountid());
		}
		InventoryItem i = new InventoryItem(iDto.getId(),d,iDto.getUnitPrice(),iDto.getQuantity(),iDto.getName(),iDto.getDescription(),iDto.getImage());
		i.setDepartment(d);
		i.setDiscount(di);
		i = inventoryItemService.createOrUpdate(i);
		iDto.setId(i.getId());
		return new ResponseEntity<InventoryItemDto>(iDto, HttpStatus.OK);
	}

	@RequestMapping(value="/getAll",method=(RequestMethod.GET),produces=(MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<List<InventoryItemDto>> getAllItems(){
		List<InventoryItemDto> dtos = new ArrayList<InventoryItemDto>();
		List<InventoryItem> items = inventoryItemService.getAll();
		for(InventoryItem i : items) {
			int dId = -1;
			if(i.getDiscount() != null) {
				dId = i.getDiscount().getDiscountID();
			}
			dtos.add(new InventoryItemDto(i.getId(),
					i.getDepartment().getId(),
					i.getUnitPrice(),
					i.getQuantity(),
					i.getName(),
					i.getDescription(),
					dId,
					i.getImage()));
		}
		return new ResponseEntity<List<InventoryItemDto>>(dtos, HttpStatus.OK);
	}
	
	@RequestMapping(value="/get",method=(RequestMethod.GET),
			produces=(MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<InventoryItem> getLineId(int id){
		return new ResponseEntity<InventoryItem>(inventoryItemService.getById(id), HttpStatus.OK);
	}

	@RequestMapping(value="/update",method=(RequestMethod.POST),
			consumes=(MediaType.APPLICATION_JSON_VALUE),
			produces=(MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<InventoryItemDto> updateItem(@RequestBody InventoryItemDto iDto){
		Department d = dService.getById(iDto.getDepartmentid());
		Discount di = null;
		System.out.println(iDto.getDiscountid());
		if(iDto.getDiscountid() != -1) {
			di = discountService.getById(iDto.getDiscountid());
			System.out.println(di);
			iDto.setDiscountid(di.getDiscountID());
			System.out.println(iDto.getDiscountid());
		}
		InventoryItem i = new InventoryItem(iDto.getId(),d,iDto.getUnitPrice(),iDto.getQuantity(),iDto.getName(),iDto.getDescription(),iDto.getImage());
		i.setDepartment(d);
		i.setDiscount(di);
		i = inventoryItemService.createOrUpdate(i);
		iDto.setId(i.getId());
		return new ResponseEntity<InventoryItemDto>(iDto, HttpStatus.OK);
	}

	@RequestMapping(value="/remove",method=(RequestMethod.POST),
			consumes=(MediaType.APPLICATION_JSON_VALUE),
			produces=(MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<String> removeItem(@RequestBody InventoryItemDto iDto){
		Department d = dService.getById(iDto.getDepartmentid());
		Discount di = null;
		if(iDto.getDiscountid() != -1) {
			di = discountService.getById(iDto.getDiscountid());
		}
		InventoryItem i = new InventoryItem(iDto.getId(),d,iDto.getUnitPrice(),iDto.getQuantity(),iDto.getName(),iDto.getDescription(),iDto.getImage());
		inventoryItemService.remove(i);
		return new ResponseEntity<String>("true", HttpStatus.OK);
	}

	@RequestMapping(value = "/upload", method = (RequestMethod.POST))
	public ResponseEntity<String> handlePictureUpload(
			@RequestParam("file") MultipartFile f) {

		String pub= "QUtJQUpRWkZQWVVKR0FVN0ZLQlE=";
		byte[] decodedBytes = Base64.getDecoder().decode(pub.getBytes());
		pub = new String(decodedBytes);

		String priv= "b3lNUCt6VnUzQ3FleENGN0NiK0p3WlFtdTZsRkc4R2JadlVHbkJKeA==";
		decodedBytes = Base64.getDecoder().decode(priv.getBytes());
		priv = new String(decodedBytes);

		BasicAWSCredentials awsCreds = new BasicAWSCredentials(pub, priv);
		AmazonS3 client = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(awsCreds))
				.withRegion(Regions.US_EAST_1)
				.build();
		File file = new File(f.getOriginalFilename());

		try {
			f.transferTo(file);
			System.out.println("Uploading a new object to S3 from a file:"+f.getName()+"\n");
			client.putObject(new PutObjectRequest(
					bucketname, f.getOriginalFilename(), file));


		} catch (AmazonServiceException ase) {
			System.out.println((("Caught an AmazonServiceException, which " +
					"means your request made it " +
					"to Amazon S3, but was rejected with an error response" +
					" for some reason"+            
					"Error Message:    " + ase.getMessage() +
					" HTTP Status Code: " + ase.getStatusCode() +
					"AWS Error Code:   " + ase.getErrorCode())+
					"Error Type:       " + ase.getErrorType() +
					"Request ID:       " + ase.getRequestId()));
		} catch (AmazonClientException ace) {
			System.out.println((("Caught an AmazonClientException, which " +
					"means the client encountered " +
					"an internal error while trying to " +
					"communicate with S3, " +
					"such as not being able to access the network." +
					"Error Message: " + ace.getMessage())));
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("file link: https://s3.amazonaws.com/"+bucketname+"/"+f.getOriginalFilename());
		ResponseEntity<String> r = new ResponseEntity<String>("https://s3.amazonaws.com/"+bucketname+"/"+f.getOriginalFilename(),HttpStatus.OK);
		System.out.println(r.getBody());
		return new ResponseEntity<String>("https://s3.amazonaws.com/"+bucketname+"/"+f.getOriginalFilename(), HttpStatus.OK);
	

	}
	@RequestMapping(value="/removeDiscount",method=(RequestMethod.POST),
			consumes=(MediaType.APPLICATION_JSON_VALUE),
			produces=(MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<InventoryItemDto> removeDiscount(@RequestBody InventoryItemDto iDto, Discount di){
		Department d = dService.getById(iDto.getDepartmentid());
		//di.setDiscountID(iDto.getDiscountid());
		//	if(di.getDiscount_Type() == 1) {
		//		iDto.setUnitPrice(iDto.getUnitPrice()+ iDto.getUnitPrice()*di.getAmount());
		//	}
		//	else {
		//		iDto.setUnitPrice(iDto.getUnitPrice()+di.getAmount());
		//	}
		di = null;
		InventoryItem i = new InventoryItem(iDto.getId(),d,iDto.getUnitPrice(),iDto.getQuantity(),iDto.getName(),iDto.getDescription(),iDto.getImage());
		i.setDepartment(d);
		i.setDiscount(di);
		i = inventoryItemService.createOrUpdate(i);
		iDto.setId(i.getId());
		iDto.setDiscountid(-1);
		return new ResponseEntity<InventoryItemDto>(iDto, HttpStatus.OK);

		
		
	}
	
	@RequestMapping("/countByDept")
	public List<Object> findByItemsByDept() {
		return inventoryItemService.findDeptCount();
	}
	
	@RequestMapping("/countDiscountsByDept")
	public List<Object> findByDiscountedItemsByDept() {
		return inventoryItemService.findByDiscountedItemsByDept();
	}
}
