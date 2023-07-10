import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('Seller e2e test', () => {
  const sellerPageUrl = '/seller';
  const sellerPageUrlPattern = new RegExp('/seller(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const sellerSample = {};

  let seller;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/sellers+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/sellers').as('postEntityRequest');
    cy.intercept('DELETE', '/api/sellers/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (seller) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/sellers/${seller.id}`,
      }).then(() => {
        seller = undefined;
      });
    }
  });

  it('Sellers menu should load Sellers page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('seller');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Seller').should('exist');
    cy.url().should('match', sellerPageUrlPattern);
  });

  describe('Seller page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(sellerPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Seller page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/seller/new$'));
        cy.getEntityCreateUpdateHeading('Seller');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', sellerPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/sellers',
          body: sellerSample,
        }).then(({ body }) => {
          seller = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/sellers+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/sellers?page=0&size=20>; rel="last",<http://localhost/api/sellers?page=0&size=20>; rel="first"',
              },
              body: [seller],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(sellerPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Seller page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('seller');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', sellerPageUrlPattern);
      });

      it('edit button click should load edit Seller page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Seller');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', sellerPageUrlPattern);
      });

      it('edit button click should load edit Seller page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Seller');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', sellerPageUrlPattern);
      });

      it('last delete button click should delete instance of Seller', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('seller').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', sellerPageUrlPattern);

        seller = undefined;
      });
    });
  });

  describe('new Seller page', () => {
    beforeEach(() => {
      cy.visit(`${sellerPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Seller');
    });

    it('should create an instance of Seller', () => {
      cy.get(`[data-cy="busineessName"]`).type('Pants Hat red').should('have.value', 'Pants Hat red');

      cy.get(`[data-cy="invoiceCounter"]`).type('9737').should('have.value', '9737');

      cy.get(`[data-cy="phoneNumber"]`).type('functionalities withdrawal SDD').should('have.value', 'functionalities withdrawal SDD');

      cy.get(`[data-cy="email"]`).type('Eleazar.Oberbrunner7@hotmail.com').should('have.value', 'Eleazar.Oberbrunner7@hotmail.com');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        seller = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', sellerPageUrlPattern);
    });
  });
});
