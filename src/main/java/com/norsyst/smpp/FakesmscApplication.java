package com.norsyst.smpp;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import webvas.utiles.ConfigProp;
import webvas.utiles.GC;

@SpringBootApplication
public class FakesmscApplication {

	private static Logger LOG = LogManager.getLogger(FakesmscApplication.class);
	public static Semaphore semaphoreSendRest = new Semaphore(3000);
	public static ExecutorService execServiceSubmit = Executors.newFixedThreadPool(3000);
	public static ServerSMPP serversmpp;
	public static Logica logica;
	public static String APP_NAME;
	
	@Bean
	Iniciador myBean() {
		return new Iniciador();
	}
	
	public static void main(String[] args) {
		ConfigProp.init(FakesmscApplication.class);
		SpringApplication application = new SpringApplication(FakesmscApplication.class);
		application.setBannerMode(Mode.OFF);
		ConfigurableApplicationContext context = application.run(args);
		APP_NAME = context.getEnvironment().getProperty("spring.application.name");
		
		Iniciador myInit = context.getBean(Iniciador.class);
		myInit.esperando();
	}

	public static class Iniciador {
		@PostConstruct
		public void init() {
			LOG.info("...");

			LOG.info("Iniciado");

		}

		public void cerrarTodo() {
			try {
				LOG.info("Cerrando Todas las conexiones");
				try {
					Set<Map.Entry<String, BindSession>> entries = serversmpp.bindSessions.bindSessionTreeMap.entrySet();

					for (Map.Entry<String, BindSession> r : entries) {
						r.getValue().session.close();
					}
				} catch (Exception e) {
					LOG.error(e.getMessage());
				}
				LOG.info("Conexiones cerradas");
			} catch (Exception e) {
				LOG.error("An error occurred performing daily restart", e);
			}
		}

		public void esperando() {
			LOG.info("...");
			logica = new Logica();
			serversmpp = new ServerSMPP();
			GC.timeInMinutes = 30;
			GC.gcColectStart();
			LOG.info("En Linea");
			serversmpp.start();
		}

		@PreDestroy
		public void destroy() {
			LOG.info("...");
			
			serversmpp.stop();
			execServiceSubmit.shutdown();
			cerrarTodo();

			GC.gcColectStop();
			LOG.info("Finalizado");

		}

	}
}
