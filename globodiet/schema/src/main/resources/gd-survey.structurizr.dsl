workspace {

    !impliedRelationships "false" 
    !identifiers "hierarchical" 

    model {
        DitaGDParams = softwareSystem "Dita GD Params" "Software system having all the GD Params entities." 
    }

    views {
        systemContext DitaGDParams "SystemContext" "An example of a System Context diagram." {
            include DitaGDParams 
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

