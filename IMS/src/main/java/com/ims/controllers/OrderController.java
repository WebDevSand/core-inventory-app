package com.ims.controllers;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.ims.beans.LineItem;
import com.ims.beans.Order;
import com.ims.services.CustomerService;
import com.ims.services.LineItemService;
import com.ims.services.OrderService;

@RestController
@RequestMapping(value = "/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	@Autowired
	CustomerService cservice;
	@Autowired
	private LineItemService lservice;
	
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	public void setLservice(LineItemService lservice) {
		this.lservice = lservice;
	}
	@RequestMapping(value="/create",method=(RequestMethod.POST),
			consumes=(MediaType.APPLICATION_JSON_VALUE),
			produces=(MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<Order> createOrder(@RequestBody Order o){
		
		Order ord = orderService.createOrUpdateOrder(o);
		return new ResponseEntity<Order>(ord, HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/update",method=(RequestMethod.POST),
			consumes=(MediaType.APPLICATION_JSON_VALUE),
			produces=(MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<Order> updateOrder(@RequestBody Order o){
		System.out.println("Creating card: " + o.getId());
		return new ResponseEntity<Order>(orderService.createOrUpdateOrder(o), HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/getAll",method=(RequestMethod.GET),
			produces=(MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<List<Order>> getAllOrders(){
		return new ResponseEntity<List<Order>>(orderService.getAllOrders(), HttpStatus.OK);
		
	}
	

	@RequestMapping(value="/getAllByCustomerId",method=(RequestMethod.GET),
			produces=(MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<List<Order>> getAllOrdersFromCustomerId(int id){
		return new ResponseEntity<List<Order>>(orderService.getAllOrdersFromCustomerId(id), HttpStatus.OK);
		
	}
	

	@RequestMapping(value="/getOrder",method=(RequestMethod.GET),
			produces=(MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<Order> getOrder(int id){
		return new ResponseEntity<Order>(orderService.getOrder(id), HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/getPrice",method=(RequestMethod.GET),
			produces=(MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<Integer> getPrice(int id){
		
		//get order obj
		Order o = orderService.getOrder(id);
		//get all line items by order
		List<LineItem> items = lservice.getAllLineItemsByOrder(o);
		
		//calculate price
		int price = 0;
		for(LineItem i : items) {
			//price = price + unitprice * quantity
			price += (i.getInventoryItem().getUnitPrice() * i.getQuantity());
		}
		
		return new ResponseEntity<Integer>(price, HttpStatus.OK);
		
	}


	@RequestMapping(value="/delete",method=(RequestMethod.POST),
			consumes=(MediaType.APPLICATION_JSON_VALUE),
			produces=(MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<String> removeOrder(@RequestBody Order o){
		System.out.println("removing order: ");
		o.setCustomer(null);
		orderService.deleteOrder(o);
		return new ResponseEntity<String>("true", HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/sendEmail",method=(RequestMethod.POST),
			consumes=(MediaType.APPLICATION_JSON_VALUE),
			produces=(MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<String> sendEmailOrder(@RequestBody Order o){
	
		//Order ord = orderService.getOrder(o.getId());
		String to = o.getCustomer().getEmail();

		// Sender's email ID needs to be mentioned
		String from = "steven.leighton95@gmail.com";
		final String username = "steven.leighton95@gmail.com";//change accordingly
		final String password = "kaexrmnbykkvlakk";//change accordingly

		// Assuming you are sending email through gmail
		String host = "smtp.gmail.com";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");

		// Get the Session object.
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));

			// Set Subject: header field
			message.setSubject("WorldTree Order Confirmation");

			List<LineItem> items = lservice.getAllLineItemsByOrderId(o.getId());
			StringBuilder textMessage = new StringBuilder();
			textMessage.append("Hello "+o.getCustomer().getFirstname() +", you have recently made an order on WorldTree!"+
					" Order id: " +o.getId()+" and order date: " +o.getOrder_Date()+")."+ "\nYour items: \n\n");
			float total = 0;
			for(LineItem i : items) {
				float price = i.getQuantity()*i.getInventoryItem().getUnitPrice();
				total += price;
				textMessage.append("$" + String.valueOf(price) + " : $"+i.getInventoryItem().getUnitPrice()+ " x " + String.valueOf(i.getQuantity())
									+"    "+ i.getInventoryItem().getName()+"\n");
			}
			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(2);
			textMessage.append("Total: $" + df.format(total) + "\n\n");
			textMessage.append("Thank you for shopping at World Tree!\n-----------------------------------------\n");
			textMessage.append("http://ec2-34-207-186-87.compute-1.amazonaws.com:8080/WorldTree");
			
			
			// Now set the actual message
			message.setText(textMessage.toString());

			// Send message
			Transport.send(message);

			//logger.info("Email login info sent to: "+user.getEmail());

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		return new ResponseEntity<String>("sent!", HttpStatus.OK);
		
	}
}
