workspace {

    !impliedRelationships "false" 
    !identifiers "hierarchical" 

    model {
        ProjectxxxEntities = softwareSystem "Project xxx Entities" "Software system having all the xxx entities." {
            surveydom = container "survey.dom" {
                Campaign = component "Campaign" "A  campaign defines a part of a food consumption survey that contains several interviews. Campaigns can be defined to be the whole study, a seasonal part of a study, a regional part of a study etc." "Entity" 
                Survey = component "Survey" "A survey collects one or more campaigns." "Entity" 
            }
        }
        ProjectxxxEntities.surveydom.Campaign -> ProjectxxxEntities.surveydom.Survey "survey￪" 
    }

    views {
        systemContext ProjectxxxEntities "SystemContext" "An example of a System Context diagram." {
            include ProjectxxxEntities 
        }

        container ProjectxxxEntities "a0" "a1" {
            include ProjectxxxEntities.surveydom 
            autolayout tb 300 300 
        }

        component ProjectxxxEntities.surveydom "survey0dom" "no desc" {
            include ProjectxxxEntities.surveydom.Campaign 
            include ProjectxxxEntities.surveydom.Survey 
            autolayout tb 300 300 
        }

        styles {
            element "Person" {
                shape "Person" 
                background "#08427b" 
                color "#ffffff" 
            }
            element "Software System" {
                background "#1168bd" 
                color "#ffffff" 
            }
        }

    }

}

