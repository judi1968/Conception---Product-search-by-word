<!DOCTYPE html>
<html lang="mg">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <title>Document</title>
    <style>
        .search-part{
            display: flex;
            justify-content: center;
            padding: 10px 0px 10px 0px;
        }

        .search-part input{
            width: 50%;
            height: 50px;
            box-shadow: 10px 10px 10px rgb(210, 210, 210);
            border: 1px solid grey;
            border-radius: 5px;
            padding: 5px;
        }

        .search-part input::placeholder{
            padding: 5px;
            font-size: 100%;
        }
        

        .result-part{
            display: flex;
            justify-content: center;
            margin-left: 20%;
            margin-right: 20%;
            margin-top: 100px;
        }

        .result-part table{
            box-shadow: 10px 10px 10px rgb(210, 210, 210);
        }

        .result-part thead{
            box-shadow: 0px 5px 3px rgb(210, 210, 210);
            margin-bottom: 2px;
        }
    </style>
</head>
<body>
    <div class="row search-part">
        <input type="text" id="searchInput" placeholder="recherche ..." onkeyup="searchProduct()">
    </div>
    <div class="row result-part">
        <table class="table table-hover" id="productTable">
            <thead>
                <tr>
                    <th scope="col">Nom du produit</th>
                    <th scope="col">Prix</th>
                    <th scope="col">qualite</th>
                </tr>
            </thead>
            <tbody>
                <!-- Les résultats de la recherche seront affichés ici -->
            </tbody>
        </table>
    </div>
    <script src="${pageContext.request.contextPath}/js/style.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-3.6.4.min.js"></script>
    <script>
        function searchProduct() {
            var searchValue = $('#searchInput').val();
            
            // Envoyer la requête Ajax
            $.ajax({
                type: "GET",
                url: "${pageContext.request.contextPath}/produit/"+searchValue, // URL de votre contrôleur Spring
                success: function (result) {
                    // Effacer le contenu du tableau
                    $('#productTable tbody').empty();

                    // Remplir le tableau avec les résultats de la recherche
                    $.each(result.produits, function (index, produit) {
                        $('#productTable tbody').append('<tr>' +
                            '<td>' + produit.designationProduit + '</td>' +
                            '<td>' + produit.prix + '</td>' +
                            '<td>' + produit.qualite + '</td>' +
                            '</tr>');
                    });
                },
                error: function (xhr, status, error) {
                    console.error("Erreur lors de la requête Ajax: " + status + " - " + error);
                }
            });
        }
    </script>
</body>
</html>
