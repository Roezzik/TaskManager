package task.manager.controllers.io;

import task.manager.model.Journal;
import java.io.IOException;

interface Marshalled {
	Journal read(String path) throws IOException;
	void write(Journal journal, String path);
}
