package sk.rdy.rdmvcframework.controller.constants;

public interface ControllerProtocol {
	int VIEW_REQUEST_QUIT = 101; // empty
	int VIEW_REQUEST_UPDATE = 102; // empty
	int VIEW_REQUEST_READ = 103; // empty
	int VIEW_REQUEST_CREATE = 104;
	int VIEW_REQUEST_DELETE = 105;
	
	int CONTROLLER_QUIT = 201; // empty
	int CONTROLLER_UPDATE_STARTED = 202; // empty
	int CONTROLLER_UPDATE_FINISHED = 203; // empty
	int CONTROLLER_DATA = 204; // obj = (ModelData) data}
}
