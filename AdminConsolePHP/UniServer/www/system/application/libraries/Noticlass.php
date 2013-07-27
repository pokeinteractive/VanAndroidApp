<?php


class Noticlass {
	
	
	function sendNoti($uuid, $message="", $count=0) {
			
		// 16c7105ee37b8ff3cb5b692ac7a060d9d5511ebc2b509d0d0998974943ecfa48
		// Put your device token here (without spaces):
		$deviceToken = $uuid; //'16c7105ee37b8ff3cb5b692ac7a060d9d5511ebc2b509d0d0998974943ecfa48';
		
		// Put your private key's passphrase here:
		$passphrase = 'passwd01';
		
		// Put your alert message here:
		//$message = 'Weddingindo notification!';
		
		////////////////////////////////////////////////////////////////////////////////
		
		$ctx = stream_context_create();
		stream_context_set_option($ctx, 'ssl', 'local_cert', '/home/weddingido/html/system/application/config/weddingido_push.pem');
		stream_context_set_option($ctx, 'ssl', 'passphrase', $passphrase);
		
		// Open a connection to the APNS server
		$fp = stream_socket_client(
			'ssl://gateway.sandbox.push.apple.com:2195', $err,
			$errstr, 60, STREAM_CLIENT_CONNECT|STREAM_CLIENT_PERSISTENT, $ctx);
		
		if (!$fp)
			exit("Failed to connect: $err $errstr" . PHP_EOL);
		
		echo 'Connected to APNS' . PHP_EOL;
		
		// Create the payload body
		$body['aps'] = array(
			'alert' => $message,
			'sound' => 'default',
			"badge" =>  $count,
			);
		
		// Encode the payload as JSON
		$payload = json_encode($body);
		
		// Build the binary notification
		$msg = chr(0) . pack('n', 32) . pack('H*', $deviceToken) . pack('n', strlen($payload)) . $payload;
		
		// Send it to the server
		$result = fwrite($fp, $msg, strlen($msg));
		
		if (!$result)
		echo 'Message not delivered' . PHP_EOL;
		else
		echo 'Message successfully delivered' . PHP_EOL;
		
		$sentResult = false;
		if (!$result) {
			$sentResult = true;
		}
		else {
			$sentResult = false;
		}
			
		
		// Close the connection to the server
		fclose($fp);
		
		return $sentResult;
	}

}

?>