package com.crm.restController.example;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.crm.common.HttpStatus;
import com.crm.common.Result;
import com.crm.model.entity.Notification;
import com.crm.service.notification.NotificationService;
import com.crm.utils.MessageBroker;
import com.crm.utils.UserProfileUtils;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/example/notification/function")
@Slf4j
public class NotificationIndexRestController {

	@Autowired
	private SimpMessagingTemplate template;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private MessageBroker messageBroker;

	@GetMapping
	public ResponseEntity<Result<Page<Notification>>> all(@RequestParam("page") int page) {
		return ResponseEntity.ok(new Result<Page<Notification>>(HttpStatus.ok, "查詢成功!", notificationService.all(page)));
	}

	@PostMapping
	public ResponseEntity<Result<String>> save(@RequestBody @Valid Notification notification, BindingResult br)
			throws Exception {
		if (br.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			for (ObjectError index : br.getAllErrors()) {
				sb.append(index.getDefaultMessage() + "<br />");
			}
			return ResponseEntity.ok(new Result<String>(HttpStatus.exception, sb.toString()));
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HHmm");
		Date current = new Date();
		notification.setCreateDate(sdf.format(current));
		notification.setCreater(UserProfileUtils.getUsername());
		notificationService.save(notification);
		messageBroker.broadcast(notification.getTitle(), notification.getBulletin());
		return ResponseEntity.ok(new Result<String>(HttpStatus.ok, "新增成功!"));
	}

//	@GetMapping
//	public void sendMyUser(Principal principal) {
//		
//		WebSocketSession webSocketSession = SocketManager.get("spongebob");
//		log.info("webSocketSession "+webSocketSession);
//		if (webSocketSession != null) {
//			log.info("sessionId = {}", webSocketSession.getId());
//			template.convertAndSendToUser("spongebob", "/queue/sendUser", principal.getName() + " : HI HI");
//		}
//		
//	}
}
