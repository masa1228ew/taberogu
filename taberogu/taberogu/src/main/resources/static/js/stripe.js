const stripe = Stripe('pk_test_51Q5unLBZ4UD9z1bMve2FOhVXnfb3Nzb51ghCQFK8KzKI0je1qfgayfTBcja04miG1xNMBZKQqXH9hfJmgRJ36rFC00Qte1CdJE');
 const paymentButton = document.querySelector('#checkout-button');
 
 paymentButton.addEventListener('click', () => {
   stripe.redirectToCheckout({
     sessionId: sessionId
   }).then(function (result) {
        if (result.error) {
            // エラーメッセージを表示する
            alert(result.error.message);
        }
     });
 });