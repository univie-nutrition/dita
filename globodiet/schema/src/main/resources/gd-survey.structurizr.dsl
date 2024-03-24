workspace {

    !impliedRelationships "false" 
    !identifiers "hierarchical" 

    model {
        ProjectxxxEntities = softwareSystem "Project xxx Entities" "Software system having all the xxx entities." 
    }

    views {
        systemContext ProjectxxxEntities "SystemContext" "An example of a System Context diagram." {
            include ProjectxxxEntities 
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

