package at.irian.cdiatwork.ideafork.ee.frontend.jsf.view.controller.idea;

import at.irian.cdiatwork.ideafork.core.api.domain.idea.Idea;
import at.irian.cdiatwork.ideafork.ee.backend.service.IdeaService;
import at.irian.cdiatwork.ideafork.ee.frontend.jsf.view.config.Pages;
import at.irian.cdiatwork.ideafork.ee.frontend.jsf.view.controller.ViewController;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.Stack;

@ViewController
public class IdeaDetailsViewCtrl implements Serializable {
    @Inject
    private IdeaService ideaService;

    private Idea currentIdea;

    private Stack<Idea> displayedIdeas = new Stack<Idea>();

    public Class<? extends Pages.Idea> showIdea(Idea currentIdea) {
        this.currentIdea = currentIdea;
        return Pages.Idea.Details.class;
    }

    public void showOriginal() {
        displayedIdeas.push(currentIdea);
        currentIdea = ideaService.loadById(currentIdea.getBaseIdeaId());
    }

    public Class<? extends Pages.Idea> back() {
        if (displayedIdeas.empty()) {
            return Pages.Idea.List.class;
        }
        currentIdea = displayedIdeas.pop();
        return null;
    }

    public Idea getCurrentIdea() {
        return currentIdea;
    }
}
