package at.irian.cdiatwork.ideafork.ee.frontend.jsf.view.config;

import at.irian.cdiatwork.ideafork.ee.frontend.jsf.view.controller.IndexViewCtrl;
import at.irian.cdiatwork.ideafork.ee.frontend.jsf.view.controller.idea.IdeaListViewCtrl;
import at.irian.cdiatwork.ideafork.ee.frontend.jsf.view.controller.promotion.PromotionRequestListViewCtrl;
import at.irian.cdiatwork.ideafork.ee.frontend.jsf.view.controller.promotion.PromotionWizardCtrl;
import at.irian.cdiatwork.ideafork.ee.frontend.jsf.view.security.UserAwareAccessDecisionVoter;
import org.apache.deltaspike.core.api.config.view.DefaultErrorView;
import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.apache.deltaspike.core.api.config.view.controller.ViewControllerRef;
import org.apache.deltaspike.core.api.config.view.navigation.NavigationParameter;
import org.apache.deltaspike.core.spi.config.view.ViewConfigNode;
import org.apache.deltaspike.core.spi.config.view.ViewConfigRoot;
import org.apache.deltaspike.jsf.api.config.view.Folder;
import org.apache.deltaspike.jsf.api.config.view.View;
import org.apache.deltaspike.security.api.authorization.Secured;

import static org.apache.deltaspike.jsf.api.config.view.View.NavigationMode.REDIRECT;
import static org.apache.deltaspike.jsf.api.config.view.View.ViewParameterMode.INCLUDE;

@ViewConfigRoot(configDescriptorValidators = IdeaForkViewMetaDataValidator.class)
@View(navigation = REDIRECT)
public interface Pages extends ViewConfig {
    @ViewControllerRef(IndexViewCtrl.class)
    class Index implements Pages {}

    @Secured(UserAwareAccessDecisionVoter.class)
    interface SecuredPages extends Pages {}

    interface User extends Pages {
        @EntryPoint
        class Login extends DefaultErrorView {}

        @EntryPoint
        class Registration implements User {}

        class Profile implements SecuredPages {}
    }

    interface Idea extends SecuredPages {
        @EntryPoint
        class Overview implements Idea {}

        class Create implements Idea {}
        class Edit implements Idea {}

        @ViewControllerRef(IdeaListViewCtrl.class)
        class List implements Idea {}
        class Details implements Idea {}
    }

    interface Search extends SecuredPages {
        class Fork implements Search {}
    }

    interface Import extends SecuredPages {
        class Upload implements Import {}
        class Summary implements Import {}
    }

    interface Promotion extends SecuredPages {
        interface Selection extends Promotion {
            @View(viewParams = INCLUDE)
            @NavigationParameter(key = "searchHint", value = "*")
            @ViewControllerRef(PromotionRequestListViewCtrl.class)
            class List implements Selection {}

            @View(name = "promote")
            class SelectPromotion implements Selection {}
        }

        @Folder(name = "wizard")
        @Wizard //all wizard-steps inherit this meta-data
        interface PromotionWizard extends Promotion {
            @EntryPoint
            @ViewControllerRef(PromotionWizardCtrl.class)
            class Step1 implements PromotionWizard {}

            class Step2 implements PromotionWizard {}

            @View(name = "summary")
            class FinalStep implements PromotionWizard {}
        }
    }
}
