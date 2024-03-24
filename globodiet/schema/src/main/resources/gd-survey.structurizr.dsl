workspace {

    !impliedRelationships "false" 
    !identifiers "hierarchical" 

    model {
        ProjectxxxEntities = softwareSystem "Project xxx Entities" "Software system having all the xxx entities." {
            surveyentities = container "survey.entities" {
                Campaign = component "Campaign" "A  campaign defines a part of a food consumption survey that contains several interviews. Campaigns can be defined to be the whole study, a seasonal part of a study, a regional part of a study etc." "Entity" 
                Survey = component "Survey" "A survey collects one or more campaigns." "Entity" 
            }
        }
        ProjectxxxEntities.surveyentities.Campaign -> ProjectxxxEntities.surveyentities.Survey "survey￪" 
    }

    views {
        systemContext ProjectxxxEntities "SystemContext" "An example of a System Context diagram." {
            include ProjectxxxEntities 
        }

        container ProjectxxxEntities "a0" "a1" {
            include ProjectxxxEntities.surveyentities 
            autolayout tb 300 300 
        }

        component ProjectxxxEntities.surveyentities "survey0entities" "no desc" {
            include ProjectxxxEntities.surveyentities.Campaign 
            include ProjectxxxEntities.surveyentities.Survey 
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

