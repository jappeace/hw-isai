make{

    name "rootnode (sortof)"
    group{

        name "standing still node"
        location new Vector3(-300, -300, -300)
        
        vehicle{
            name "wall"
            mesh new Box(1, 1080, 1920)
            location new Vector3(-20,300,300)
            texture "Textures/demon.jpg"
        }

        (0..600).each{ int number ->
            vehicle{
                name "z"
                location new Vector3(0,0,number * 2)
            }
            vehicle{
                name "y"
                location new Vector3(0,number * 2, 0)
            }
            vehicle{
                name "x"
                location new Vector3(number * 2,0, 0)
            }

        }

        (1..5).each{ int outer ->
            (0..600).each{int number ->
                group{
                    location new Vector3(100 * outer, 0, 100)
                    name "helix"
                    float radius = 8
                    float height = 5
                    vehicle{
                        location new Vector3(Math.sin(number/Math.PI)*radius, number*height, Math.cos(number/Math.PI)*radius)
                    }
                    vehicle{
                        location new Vector3(Math.sin(number/Math.PI + Math.PI)*radius, number*height, Math.cos(number/Math.PI+ Math.PI)*radius)
                    }
                
                }
            }
        }
        (0..50).each{ int number ->
            vehicle{
                location new Vector3(0.01 * number, 0, 0)
            }
            vehicle{

                location new Vector3(number * number *3, number*2, 5*number -30)
            }
        }
    }
    group{
        name "moving stuffNode"

        vehicle{
            name "explorer"
            mesh new Sphere(5, 5, 1)
            location new Vector3(0, -300, 0)
            behaviour{
                explore()
            }
        }

        group{
            name "flocking group"


            (0..30).each{ int number ->
                vehicle{
                    location new Vector3(number * 8 - 20, random.nextDouble() * 10 - 20, random.nextDouble() * 10)
                    behaviour{ 
                        flock() 
                    }
                    mass 0.03
                    friction 0
                }
                vehicle{
                    location new Vector3(number * 8, random.nextDouble() * 10, random.nextDouble() * 10)
                    behaviour{ 
                        flock() 
                    }
                    mass 0.05
                    friction 0
                }
                vehicle{
                    location new Vector3(number * 8 + 20, random.nextDouble() * 10 + 20, random.nextDouble() * 10)
                    behaviour{ 
                        flock() 
                    }
                    mass 0.07
                    friction 0
                }
            }
        }
        
        group{
            name "wanderers"
            location new Vector3(-600,0,0)
            (0..40).each{ int number ->

                vehicle{
                    location new Vector3(random.nextDouble() * number, random.nextDouble() *number, random.nextDouble() * number)
                    mass 1
                    behaviour{
                        wander()
                    }
                }

            }
        }
        
    }

}